package com.example.shopify.feature.navigation_bar.my_account.screens.order.model.payment

import com.example.shopify.helpers.Resource
import com.example.shopify.helpers.shopify.input_creator.ShopifyInputCreator
import com.example.shopify.helpers.shopify.mapper.ShopifyMapper
import com.example.shopify.helpers.shopify.query_generator.ShopifyQueryGenerator
import com.example.shopify.utils.shopify.enqueue
import com.shopify.buy3.CardClient
import com.shopify.buy3.CardVaultResult
import com.shopify.buy3.CreditCard
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.GraphError
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CurrencyCode
import com.shopify.buy3.Storefront.MailingAddressInput
import com.shopify.graphql.support.ID
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShopifyCreditCardPaymentStrategy
@Inject constructor(
    private val cardClient: CardClient,
    private val graphClient: GraphClient,
    private val mapper: ShopifyMapper,
    private val queryGenerator: ShopifyQueryGenerator,
    private val inputCreator: ShopifyInputCreator,
    private val defaultDispatcher: CoroutineDispatcher
) : PaymentStrategy<@JvmSuppressWildcards ShopifyCreditCardPaymentStrategy.ShopifyPaymentInfo, @JvmSuppressWildcards Flow<Resource<ShopifyCreditCardPaymentStrategy.PaymentResult>>> {

    override fun pay(paymentInfo: ShopifyPaymentInfo): Flow<Resource<PaymentResult>> =
        flow {
            paymentInfo.apply {
                val cardVaultUrl = getCardVaultUrl().getOrThrow()
                val checkoutToken = getCheckoutToken(creditCard, cardVaultUrl).getOrThrow()
                val input = inputCreator.createCreditCardPaymentInputV2(
                    amount = amount,
                    currency = currency,
                    billingAddress = billingAddress,
                    creditCardVaultToken = checkoutToken
                )
                val checkoutCompletionAvailability =
                    completeCheckout(checkoutID = checkoutId, input = input).getOrThrow()
                if (!checkoutCompletionAvailability.paymentReady && !checkoutCompletionAvailability.checkoutReady) {
                    emit(PaymentResult(checkoutCompletionAvailability))
                } else {
                    handlePolling(ID(checkoutCompletionAvailability.paymentId))
                }
            }
        }
            .map { Resource.Success(it) }
            .catch { Resource.Error(mapper.map(it as GraphError)) }
            .flowOn(defaultDispatcher)

    /*
    listen to server till payment is done
    or retry 12 time when failure
     */
    @Throws(Exception::class)
    private suspend fun handlePolling(paymentId: ID): PaymentResult {
        var failureAttempts = 0
        var exception: Exception = GraphError.Unknown()
        do {
            try {
                val paymentResult = pollingCheckoutCompletion(paymentId).getOrThrow()
                if (paymentResult.ready)
                    return paymentResult
                delay(500)
            } catch (e: Exception) {
                failureAttempts++
                exception = e
            }
        } while (failureAttempts != 0)
        throw exception
    }


    private suspend fun getCardVaultUrl(): Result<String> {
        val deferred = CompletableDeferred<Result<String>>()
        graphClient.queryGraph(queryGenerator.generateServerUrlQuery()).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    result.response
                        .data
                        ?.shop
                        ?.paymentSettings
                        ?.cardVaultUrl?.let { deferred.complete(Result.success(it)) }
                        ?: deferred.complete(Result.failure(GraphError.Unknown()))


                is GraphCallResult.Failure ->
                    deferred.complete(Result.failure(result.error))
            }
        }
        return deferred.await()
    }

    private suspend fun getCheckoutToken(
        creditCard: CreditCard,
        cardVaultUrl: String
    ): Result<String> {
        val deferred = CompletableDeferred<Result<String>>()
        cardClient.vault(creditCard, cardVaultUrl).enqueue { result ->
            when (result) {
                is CardVaultResult.Success ->
                    deferred.complete(Result.success(result.token))

                is CardVaultResult.Failure ->
                    deferred.complete(Result.failure(GraphError.Unknown()))
            }
        }
        return deferred.await()
    }

    private suspend fun completeCheckout(
        checkoutID: ID,
        input: Storefront.CreditCardPaymentInputV2
    ): Result<PaymentCompletionAvailability> {
        val deferred = CompletableDeferred<Result<PaymentCompletionAvailability>>()
        val query =
            queryGenerator.generatePaymentCompletionAvailabilityQuery(checkoutID, input)
        graphClient.enqueue(query).map { result ->
            when (result) {
                is GraphCallResult.Success ->
                    deferred.complete(
                        Result.success(
                            mapper.mapToPaymentCompletionAvailability(result.response)
                        )
                    )

                is GraphCallResult.Failure ->
                    deferred.complete(Result.failure(result.error))

            }
        }
        return deferred.await()
    }

    private suspend fun pollingCheckoutCompletion(paymentId: ID): Result<PaymentResult> {
        val deferred = CompletableDeferred<Result<PaymentResult>>()

        val query = queryGenerator.generateUpdateCheckoutReadyQuery(paymentId)
        graphClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success ->
                    deferred.complete(Result.success(mapper.mapToPaymentResult(result)))

                is GraphCallResult.Failure ->
                    deferred.complete(Result.failure(result.error))
            }
        }
        return deferred.await()
    }


    data class ShopifyPaymentInfo(
        val checkoutId: ID,
        val amount: String,
        val currency: CurrencyCode,
        val creditCard: CreditCard,
        val billingAddress: MailingAddressInput
    )

    data class PaymentCompletionAvailability(
        val errors: List<String>,
        val checkoutReady: Boolean,
        val paymentReady: Boolean,
        val paymentId: String?
    )

    data class PaymentResult(
        val paymentCompletionAvailability: PaymentCompletionAvailability? = null,
        val orderId: String? = null,
        val error: String? = null,
        val processedAt: String? = null,
        val orderNumber: Int? = null,
        val totalPrice: String? = null,
        val ready: Boolean = false
    )
}

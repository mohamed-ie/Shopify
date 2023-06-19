package com.example.shopify.feature.navigation_bar.model.repository.shopify

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.common.model.Pageable
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.LineItems
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.utils.Constants
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology

class FakeShopifyRepositoryImpl(
) : ShopifyRepository {
    private var wishList: MutableList<ID> = mutableListOf()
    override fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>> {
        TODO("Not yet implemented")
    }

    override fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult) {
        TODO("Not yet implemented")
    }

    override fun getUserInfo(): Flow<SignInUserInfoResult> {
        TODO("Not yet implemented")
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return flowOf(true)
    }

    override fun getBrands(): Flow<Resource<List<Brand>>> = flow {
        val brandList = listOf(Brand("title", "url")) // Your list of brands
        emit(Resource.Success(brandList))
    }


    override suspend fun getCart(): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByBrandName(brandName: String): Resource<List<BrandProduct>> {
        val products = listOf(
            BrandProduct(
                id = ID(""),
                title = "Ultima show Running Shoes Pink",
                images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                isFavourite = false,
                price = Storefront.MoneyV2().setAmount("200")
                    .setCurrencyCode(Storefront.CurrencyCode.SDG)
            ),
            BrandProduct(
                id = ID(""),
                title = "Ultima show Running Shoes Pink",
                images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                isFavourite = false,
                price = Storefront.MoneyV2().setAmount("200")
                    .setCurrencyCode(Storefront.CurrencyCode.SDG)
            )
        )
        return Resource.Success(products)
    }

    override fun getProductDetailsByID(id: String): Flow<Resource<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductReviewById(productId: ID, reviewsCount: Int?): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun setProductReview(productId: ID, review: Review) {
        TODO("Not yet implemented")
    }

    override suspend fun getCheckOutId(cart: Cart): Flow<Resource<ID?>> {
        TODO("Not yet implemented")
    }

    override fun getProductsCategory(
        productType: String,
        productTag: String
    ): Flow<Resource<List<BrandProduct>>> = flow {
        val products = listOf(
            BrandProduct(
                id = ID(""),
                title = "Ultima show Running Shoes Pink",
                images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                isFavourite = false,
                price = Storefront.MoneyV2().setAmount("200")
                    .setCurrencyCode(Storefront.CurrencyCode.SDG)
            ),
            BrandProduct(
                id = ID(""),
                title = "Ultima show Running Shoes Pink",
                images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                isFavourite = false,
                price = Storefront.MoneyV2().setAmount("200")
                    .setCurrencyCode(Storefront.CurrencyCode.SDG)
            )
        )
        emit(Resource.Success(products))
    }

    override fun getProductsTag(): Flow<Resource<List<String>>> = flow {
        val tags: List<String> = listOf("SHOES", "ACCESSORIES")
        emit(Resource.Success(tags))
    }

    override fun getProductsType(): Flow<Resource<List<String>>> = flow {
        val types: List<String> = listOf("men", "women")
        emit(Resource.Success(types))
    }

    override suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(addressId: ID): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getMinCustomerInfo(): Flow<Resource<MinCustomerInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCurrency(currency: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override suspend fun getAddresses(): Resource<List<Storefront.MailingAddress>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToCart(productVariantId: String, quantity: Int): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun applyCouponToCart(coupon: String): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCartShippingAddress(address: Storefront.MailingAddress): Resource<String?> {
        TODO("Not yet implemented")
    }

    override fun getShopifyProductsByWishListIDs(): Flow<Resource<Product?>> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductWishListById(productId: ID) {
        wishList.add(productId)
    }

    override suspend fun removeProductWishListById(productId: ID) {
        wishList.remove(productId)
    }

    override suspend fun getProductsByQuery(
        productQueryType: Constants.ProductQueryType,
        queryContent: String
    ): Resource<Pageable<List<BrandProduct>>?> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrders(): Flow<Resource<List<Order>>> = flow {
        val orders = listOf<Order>(
            Order(
                orderNumber = 1022,
                processedAt = DateTime(System.currentTimeMillis(), ISOChronology.getInstanceUTC()),
                subTotalPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalShippingPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                discountApplications = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalTax = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                billingAddress = Storefront.MailingAddress()
                    .setAddress1("haram - glal street - giza = ggypt")
                    .setPhone("+201117513385"),
                lineItems = listOf<LineItems>(
                    LineItems(
                        id = ID("gid://shopify/Product/8312391237939"),
                        name = "VANS | AUTHENTIC | (MULTI EYELETS) | GRADIENT/CRIMSON",
                        thumbnail = "https://cdn.shopify.com/s/files/1/0774/1662/8531/products/d841f71ea6845bf6005453e15a18c632.jpg?v=1685530920",
                        collection = "SHOES",
                        vendor = "VANS",
                        price = Storefront.MoneyV2().setAmount("50")
                            .setCurrencyCode(Storefront.CurrencyCode.EGP),
                        description = "The forefather of the Vans family, the Vans Authentic was introduced in 1966 and nearly 4 decades later is still going strong, its popularity extending from the original fans - skaters and surfers to all sorts. The Vans Authentic is constructed from canvas and Vans' signature waffle outsole construction."
                    )
                ),
                fulfillment = Storefront.OrderFulfillmentStatus.FULFILLED,
                financialStatus = Storefront.OrderFinancialStatus.PAID
            ),
            Order(
                orderNumber = 1022,
                processedAt = DateTime(System.currentTimeMillis(), ISOChronology.getInstanceUTC()),
                subTotalPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalShippingPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                discountApplications = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalTax = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                totalPrice = Storefront.MoneyV2().setAmount("50")
                    .setCurrencyCode(Storefront.CurrencyCode.EGP),
                billingAddress = Storefront.MailingAddress()
                    .setAddress1("haram - glal street - giza = ggypt")
                    .setPhone("+201117513385"),
                lineItems = listOf<LineItems>(
                    LineItems(
                        id = ID("gid://shopify/Product/8312391237939"),
                        name = "VANS | AUTHENTIC | (MULTI EYELETS) | GRADIENT/CRIMSON",
                        thumbnail = "https://cdn.shopify.com/s/files/1/0774/1662/8531/products/d841f71ea6845bf6005453e15a18c632.jpg?v=1685530920",
                        collection = "SHOES",
                        vendor = "VANS",
                        price = Storefront.MoneyV2().setAmount("50")
                            .setCurrencyCode(Storefront.CurrencyCode.EGP),
                        description = "The forefather of the Vans family, the Vans Authentic was introduced in 1966 and nearly 4 decades later is still going strong, its popularity extending from the original fans - skaters and surfers to all sorts. The Vans Authentic is constructed from canvas and Vans' signature waffle outsole construction."
                    )
                ),
                fulfillment = Storefront.OrderFulfillmentStatus.FULFILLED,
                financialStatus = Storefront.OrderFinancialStatus.PAID
            )
        )
        emit(Resource.Success(orders))
    }

    override suspend fun removeCartLines(productVariantId: String): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun changeCartLineQuantity(
        merchandiseId: String,
        quantity: Int
    ): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun completeOrder(paymentPending: Boolean): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?> {
        TODO("Not yet implemented")
    }

}
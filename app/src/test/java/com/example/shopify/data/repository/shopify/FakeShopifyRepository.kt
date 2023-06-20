package com.example.shopify.data.repository.shopify

import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
import com.example.shopify.feature.auth.screens.login.model.SignInUserInfoResult
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
import com.example.shopify.feature.auth.screens.registration.model.SignUpUserResponseInfo
import com.example.shopify.feature.navigation_bar.cart.model.Cart
import com.example.shopify.feature.navigation_bar.common.model.Pageable
import com.example.shopify.feature.navigation_bar.home.screen.home.model.Brand
import com.example.shopify.feature.navigation_bar.home.screen.product.model.BrandProduct
import com.example.shopify.feature.navigation_bar.model.repository.shopify.ShopifyRepository
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.VariantItem
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.utils.Constants
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeShopifyRepository : ShopifyRepository {

    private val wishList: MutableList<ID> = mutableListOf()

    override fun signUp(userInfo: SignUpUserInfo): Flow<Resource<SignUpUserResponseInfo>> = flow {
        emit(
            Resource.Success(
                SignUpUserResponseInfo(
                    firstName = userInfo.firstName,
                    lastName = userInfo.lastName,
                    email = userInfo.email,
                    phone = userInfo.phone,
                    "",
                    null
                )
            )
        )
    }

    override fun signIn(userInfo: SignInUserInfo): Flow<Resource<SignInUserInfoResult>> = flow {
        emit(
            Resource.Success(
                SignInUserInfoResult(
                    email = userInfo.email,
                    password = userInfo.password,
                    accessToken = "5",
                    expireTime = null,
                    error = ""
                )
            )
        )
    }

    override suspend fun saveUserInfo(userResponseInfo: SignInUserInfoResult) {
        print(userResponseInfo)
    }

    override fun getUserInfo(): Flow<SignInUserInfoResult> {
        TODO("Not yet implemented")
    }

    override fun isLoggedIn(): Flow<Boolean> = flow {
        emit(true)
    }

    override fun getBrands(): Flow<Resource<List<Brand>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByBrandName(brandName: String): Resource<List<BrandProduct>> {
        TODO("Not yet implemented")
    }

    override fun getProductDetailsByID(id: String): Flow<Resource<Product>> = flow {
        emit(
            Resource.Success(
                Product(
                    images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                    description = "The Stan Smith owned the tennis court in the '70s." +
                            " Today it runs the streets with the same clean," +
                            " classic style." +
                            " These kids' shoes preserve the iconic look of the original," +
                            " made in leather with punched 3-Stripes," +
                            " heel and tongue logos and lightweight step-in cushioning.",
                    totalInventory = 5,
                    variants = listOf(VariantItem("", "", "white/1", 0)),
                    title = "Ultima show Running Shoes Pink",
                    price = Price(
                        amount = "172.00",
                        currencyCode = "AED"
                    ),
                    discount = Discount(
                        realPrice = "249.00",
                        percent = 30
                    ),
                    vendor = "Adidas",
                )
            )
        )
    }

    override suspend fun getProductReviewById(productId: ID, reviewsCount: Int?): List<Review> =
        listOf(
            Review(
                reviewer = "",
                rate = 2.5,
                review = "Perfect phone",
                description = "Its very nice experience Ga iloved it",
                time = "2 months ago"
            )
        )


    override suspend fun setProductReview(productId: ID, review: Review) {
        TODO("Not yet implemented")
    }

    override suspend fun getCheckOutId(cart: Cart): Flow<Resource<ID?>> {
        TODO("Not yet implemented")
    }

    override fun getProductsCategory(
        productType: String,
        productTag: String,
    ): Flow<Resource<List<BrandProduct>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsTag(): Resource<List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsType(): Resource<List<String>> {
        TODO("Not yet implemented")
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

    override fun getShopifyProductsByWishListIDs(): Flow<Resource<Product?>> = flow {
        emit(
            Resource.Success(
                Product(
                    images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                    description = "The Stan Smith owned the tennis court in the '70s." +
                            " Today it runs the streets with the same clean," +
                            " classic style." +
                            " These kids' shoes preserve the iconic look of the original," +
                            " made in leather with punched 3-Stripes," +
                            " heel and tongue logos and lightweight step-in cushioning.",
                    totalInventory = 5,
                    variants = listOf(VariantItem("", "", "white/1", 0)),
                    title = "Ultima show Running Shoes Pink",
                    price = Price(
                        amount = "172.00",
                        currencyCode = "AED"
                    ),
                    discount = Discount(
                        realPrice = "249.00",
                        percent = 30
                    ),
                    vendor = "Adidas",
                )
            )
        )
    }

    override suspend fun addProductWishListById(productId: ID) {
        wishList.add(productId)
    }

    override suspend fun removeProductWishListById(productId: ID) {
        wishList.remove(productId)
    }

    override suspend fun getProductsByQuery(
        productQueryType: Constants.ProductQueryType,
        queryContent: String,
    ): Resource<Pageable<List<BrandProduct>>?> {
        return Resource.Success(
            Pageable(
                data = listOf(
                    BrandProduct(
                        id = ID("hamed"),
                        title = "Ultima show Running Shoes Pink",
                        images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
                        isFavourite = wishList.contains(ID("hamed")),
                        price = Storefront.MoneyV2().setAmount("200").setCurrencyCode(Storefront.CurrencyCode.SDG)
                    )
                ),
                hasNext = true,
                lastCursor = "Hamed"
            )
        )
    }

    override suspend fun getOrders(): Flow<Resource<List<Order>>> {
        TODO("Not yet implemented")
    }

    override suspend fun removeCartLines(productVariantId: String): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun changeCartLineQuantity(
        merchandiseId: String,
        quantity: Int,
    ): Resource<Cart?> {
        TODO("Not yet implemented")
    }

    override suspend fun completeOrder(paymentPending: Boolean): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(password: String): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun changePhoneNumber(phone: String): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun changeName(firstName: String, lastName: String): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun createUserEmail(email: String): Resource<Unit> =
        Resource.Success(Unit)
}
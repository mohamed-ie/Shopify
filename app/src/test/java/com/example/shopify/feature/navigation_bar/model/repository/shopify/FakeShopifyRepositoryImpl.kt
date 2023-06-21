package com.example.shopify.feature.navigation_bar.model.repository.shopify

import com.example.shopify.data.shopify.repository.ShopifyRepository
import com.example.shopify.model.Pageable
import com.example.shopify.feature.navigation_bar.my_account.screens.my_account.model.MinCustomerInfo
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.LineItems
import com.example.shopify.feature.navigation_bar.my_account.screens.order.model.order.Order
import com.example.shopify.feature.navigation_bar.my_account.screens.order.view.component.order.OrderItemState
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.VariantItem
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.helpers.Resource
import com.example.shopify.model.auth.signin.SignInUserInfo
import com.example.shopify.model.auth.signin.SignInUserInfoResult
import com.example.shopify.model.auth.signup.SignUpUserInfo
import com.example.shopify.model.auth.signup.SignUpUserResponseInfo
import com.example.shopify.model.cart.cart.Cart
import com.example.shopify.model.cart.cart.CartLine
import com.example.shopify.model.cart.cart.CartProduct
import com.example.shopify.model.home.Brand
import com.example.shopify.ui.home.product.model.BrandProduct
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
    private val wishList: MutableList<ID> = mutableListOf()
    private var cart =
        Cart(
            lines = listOf(
                CartLine(
                    ID(""),
                    ID(""),
                    "EGP 1500",
                    "",
                    5,
                    9,
                    CartProduct(
                        ID(""),
                        "",
                        "",
                        "",
                        ""
                    )
                )
            )
        )

    private var minCustomerInfo = MinCustomerInfo("ahmed", "ahmed@gmail.com")

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

    override fun isLoggedIn(): Flow<Boolean> {
        return flowOf(true)
    }

    override fun getBrands(): Flow<Resource<List<Brand>>> = flow {
        val brandList = listOf(Brand("title", "url")) // Your list of brands
        emit(Resource.Success(brandList))
    }


    override suspend fun getCart(): Resource<Cart?> {
        return Resource.Success(cart)
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


    override suspend fun setProductReview(productId: ID, review: Review): Resource<Unit> {
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

    override suspend fun getProductsTag(): Resource<List<String>> {
        val tags: List<String> = listOf("SHOES", "ACCESSORIES")
        return Resource.Success(tags)
    }

    override suspend fun getProductsType(): Resource<List<String>> {
        val types: List<String> = listOf("men", "women")
        return Resource.Success(types)
    }

    override suspend fun saveAddress(address: Storefront.MailingAddressInput): Resource<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(addressId: ID): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMinCustomerInfo(): Resource<MinCustomerInfo> {
        return Resource.Success(minCustomerInfo)
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
                        price = Storefront.MoneyV2().setAmount("200")
                            .setCurrencyCode(Storefront.CurrencyCode.SDG)
                    )
                ),
                hasNext = true,
                lastCursor = "Hamed"
            )
        )
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
                fulfillment = OrderItemState.Progress(),
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
                fulfillment = OrderItemState.Delivered(),
                financialStatus = Storefront.OrderFinancialStatus.PAID
            )
        )
        emit(Resource.Success(orders))
    }

    override suspend fun removeCartLines(productVariantId: String): Resource<Cart?> {
        val lines = cart.lines.toMutableList().apply {
            removeIf { productVariantId == it.productVariantID.toString() }
        }
        cart = cart.copy(lines = lines)
        return Resource.Success(cart)
    }

    override suspend fun changeCartLineQuantity(
        merchandiseId: String,
        quantity: Int
    ): Resource<Cart?> {
        val lines = cart.lines.toMutableList().apply {
            val line = find { it.productVariantID.toString() == merchandiseId }
            remove(line)
            line?.copy(quantity = quantity)?.let { add(it) }
        }
        cart = cart.copy(lines = lines)
        return Resource.Success(cart)
    }

    override suspend fun completeOrder(paymentPending: Boolean): Resource<String?> {
        return Resource.Success(null)
    }

    override suspend fun sendCompletePayment(): Resource<Pair<String?, String?>?> {
        return Resource.Success(Pair("", null))
    }

    override suspend fun changePassword(password: String): Resource<String?> {
        return Resource.Success(null)
    }

    override suspend fun changePhoneNumber(phone: String): Resource<String?> {
        return Resource.Success(null)
    }

    override suspend fun changeName(firstName: String, lastName: String): Resource<String?> {
        return Resource.Success(null)
    }

    override suspend fun createUserEmail(email: String): Resource<Unit> =
        Resource.Success(Unit)

    override suspend fun updateAddress(
        addressId: ID,
        address: Storefront.MailingAddressInput
    ): Resource<String?> {
        TODO("Not yet implemented")
    }
}
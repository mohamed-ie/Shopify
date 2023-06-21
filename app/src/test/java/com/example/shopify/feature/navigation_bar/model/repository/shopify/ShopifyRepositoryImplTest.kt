//package com.example.shopify.feature.navigation_bar.model.repository.shopify
//
//import com.example.shopify.feature.auth.screens.login.model.SignInUserInfo
//import com.example.shopify.feature.auth.screens.registration.model.SignUpUserInfo
//import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
//import com.example.shopify.helpers.getOrNull
//import com.shopify.buy3.Storefront
//import com.shopify.graphql.support.ID
//import kotlinx.coroutines.flow.first
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import kotlinx.coroutines.test.runTest
//
//class ShopifyRepositoryImplTest {
//    lateinit var repository: ShopifyRepository
//
//    @Before
//    fun setUp() {
//    }
//
//    @After
//    fun tearDown() {
//    }
//
//    @Test
//    fun signUp() = runTest {
//        val result = repository.signUp(SignUpUserInfo("", "", "", "", ""))
//        Assert.assertNotNull(result.first().getOrNull())
//    }
//
//    @Test
//    fun signIn() = runTest {
//        val result = repository.signIn(SignInUserInfo("", ""))
//        Assert.assertNotNull(result.first().getOrNull())
//    }
//
//
//    @Test
//    fun getUserInfo() = runTest {
//        val result = repository.getUserInfo(SignInUserInfo("", ""))
//        Assert.assertNotNull(result.first().getOrNull())
//    }
//
//    @Test
//    fun isLoggedIn() = runTest {
//        val result = repository.isLoggedIn(SignInUserInfo("", ""))
//        Assert.assertTrue(result.first())
//    }
//
//    @Test
//    fun getBrands() = runTest {
//        val result = repository.getBrands()
//        Assert.assertNotNull(result.first().getOrNull())
//    }
//
//    @Test
//    fun getCart() = runTest {
//        val result = repository.getCart()
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//    @Test
//    fun getProductsByBrandName() = runTest {
//        val result = repository.getProductsByBrandName("vens")
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//    @Test
//    fun getProductsByQuery() = runTest {
//        val result = repository.getProductsByQuery("vens", "a")
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//    @Test
//    fun getProductDetailsByID() = runTest {
//        val result = repository.getProductDetailsByID(ID(""))
//        Assert.assertNotNull(result.first().getOrNull())
//    }
//
//
//    @Test
//    fun getProductReviewById() = runTest {
//        val result = repository.getProductReviewById(ID(""))
//        Assert.assertNotNull(result.first())
//    }
//
//
//    @Test
//    fun setProductReview() = runTest {
//        val result = repository.setProductReview(ID(""), Review())
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun saveAddress() = runTest {
//        val result = repository.saveAddress(Storefront.MailingAddressInput())
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun deleteAddress() = runTest {
//        val result = repository.deleteAddress(ID(""))
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//    @Test
//    fun getMinCustomerInfo() = runTest {
//        val result = repository.getMinCustomerInfo()
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun getAddresses() = runTest {
//        val result = repository.getAddresses()
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun addToCart() = runTest {
//        val result = repository.addToCart(ID(""), 5)
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun removeCartLines()   =runTest{
//        val result = repository.removeCartLines(ID(""))
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//
//    @Test
//    fun completeOrder()   =runTest{
//        val result = repository.completeOrder(true)
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//    @Test
//    fun sendCompletePayment()   =runTest{
//        val result = repository.getMinCustomerInfo()
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//
//    @Test
//    fun changeCartLineQuantity()   =runTest{
//        val result = repository.changeCartLineQuantity(ID(""),5)
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//
//    @Test
//    fun applyCouponToCart() =runTest{
//        val result = repository.applyCouponToCart("")
//        Assert.assertNotNull(result.getOrNull())
//    }
//
//
//
//    @Test
//    fun changePassword() {
//    }
//
//    @Test
//    fun changePhoneNumber() {
//    }
//
//    @Test
//    fun updateCartShippingAddress() {
//    }
//
//    @Test
//    fun changeName() {
//    }
//
//    @Test
//    fun createUserEmail() {
//    }
//
//    @Test
//    fun updateAddress() {
//    }
//
//    @Test
//    fun addProductWishListById() {
//    }
//
//    @Test
//    fun removeProductWishListById() {
//    }
//
//    @Test
//    fun getOrders() {
//    }
//
//    @Test
//    fun getShopifyProductsByWishListIDs() {
//    }
//
//    @Test
//    fun getCheckOutId() {
//    }
//
//    @Test
//    fun getProductsCategory() {
//    }
//
//    @Test
//    fun getProductsTag() {
//    }
//
//    @Test
//    fun getProductsType() {
//    }
//}
package com.example.shopify.feature.wishList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Product
import com.example.shopify.feature.wishList.components.EmptyWishListScreenContent
import com.example.shopify.feature.wishList.components.WishListProductCardItem
import com.example.shopify.feature.wishList.components.WishListTopBar
import com.example.shopify.theme.shopifyColors
import com.shopify.graphql.support.ID


@Composable
fun WishListScreenContent(
    productList:List<Product>,
    back:()->Unit,
    navigateToProductDetails:(ID)->Unit,
    deleteProduct:(ID)->Unit
) {
    Scaffold(
        topBar = { WishListTopBar(itemsCount = productList.count(),back = back) },
        ) {
        if(productList.isEmpty())
            EmptyWishListScreenContent(it)
        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .background(MaterialTheme.shopifyColors.ServerColor)
                    .fillMaxSize()
                    .padding(it),
            ) {
                items(productList) { product ->
                    WishListProductCardItem(
                        product = product,
                        navigateToProductDetails = { navigateToProductDetails(product.id) },
                        deleteProduct = {deleteProduct(product.id)}
                    )
                }
            }
        }

    }
}


@Preview
@Composable
private fun WishListScreenContentPreview() {
    WishListScreenContent(
        productList = listOf(
//            Product(
//                image = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
//                description = "The Stan Smith owned the tennis court in the '70s." +
//                        " Today it runs the streets with the same clean," +
//                        " classic style." +
//                        " These kids' shoes preserve the iconic look of the original," +
//                        " made in leather with punched 3-Stripes," +
//                        " heel and tongue logos and lightweight step-in cushioning.",
//                totalInventory = 5,
//                variants = listOf(VariantItem("","","","white/1")),
//                title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
//                vendor = "Adidas",
//                price = Price(
//                    amount = "172.00",
//                    currencyCode = "AED"
//                ),
//                discount = Discount(
//                    realPrice = "249.00",
//                    percent = 30
//                )
//            ),
//            Product(
//                image = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
//                description = "The Stan Smith owned the tennis court in the '70s." +
//                        " Today it runs the streets with the same clean," +
//                        " classic style." +
//                        " These kids' shoes preserve the iconic look of the original," +
//                        " made in leather with punched 3-Stripes," +
//                        " heel and tongue logos and lightweight step-in cushioning.",
//                totalInventory = 5,
//                variants = listOf(VariantItem("","","","white/1")),
//                title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
//                vendor = "Adidas",
//                price = Price(
//                    amount = "172.00",
//                    currencyCode = "AED"
//                ),
//                discount = Discount(
//                    realPrice = "249.00",
//                    percent = 30
//                )
//            ),
//            Product(
//                image = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
//                description = "The Stan Smith owned the tennis court in the '70s." +
//                        " Today it runs the streets with the same clean," +
//                        " classic style." +
//                        " These kids' shoes preserve the iconic look of the original," +
//                        " made in leather with punched 3-Stripes," +
//                        " heel and tongue logos and lightweight step-in cushioning.",
//                totalInventory = 5,
//                variants = listOf(VariantItem("","","","white/1")),
//                title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
//                vendor = "Adidas",
//                price = Price(
//                    amount = "172.00",
//                    currencyCode = "AED"
//                ),
//                discount = Discount(
//                    realPrice = "249.00",
//                    percent = 30
//                )
//            ),
//            Product(
//                image = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
//                description = "The Stan Smith owned the tennis court in the '70s." +
//                        " Today it runs the streets with the same clean," +
//                        " classic style." +
//                        " These kids' shoes preserve the iconic look of the original," +
//                        " made in leather with punched 3-Stripes," +
//                        " heel and tongue logos and lightweight step-in cushioning.",
//                totalInventory = 5,
//                variants = listOf(VariantItem("","","","white/1")),
//                title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
//                vendor = "Adidas",
//                price = Price(
//                    amount = "172.00",
//                    currencyCode = "AED"
//                ),
//                discount = Discount(
//                    realPrice = "249.00",
//                    percent = 30
//                )
//            )
        ),
        back = {},
        navigateToProductDetails = {},
        deleteProduct = {}
    )
}
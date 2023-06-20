package com.example.shopify.feature.wishList.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.components.AddedCartBottomSheetCard
import com.example.shopify.feature.wishList.components.EmptyWishListScreenContent
import com.example.shopify.feature.wishList.components.WishListProductCardItem
import com.example.shopify.feature.wishList.components.WishListTopBar
import com.example.shopify.theme.shopifyColors
import com.shopify.graphql.support.ID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreenContent(
    productList:List<WishedProductState>,
    bottomSheetState: WishedBottomSheetState,
    continueShopping:()->Unit,
    viewCart:()->Unit,
    back:()->Unit,
    navigateToProductDetails:(ID)->Unit,
    addToCart: (Int) -> Unit,
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
                items(productList.count()) { wishedProductIndex ->
                    WishListProductCardItem(
                        product = productList[wishedProductIndex].product,
                        isAddingToCard = productList[wishedProductIndex].isAddingToCard,
                        navigateToProductDetails = { navigateToProductDetails(productList[wishedProductIndex].product.id) },
                        deleteProduct = {deleteProduct(productList[wishedProductIndex].product.id)},
                        addToCart = {addToCart(wishedProductIndex)},
                    )
                }
            }
        }

        if (bottomSheetState.expandBottomSheet) {
            val edgeToEdgeEnabled by remember { mutableStateOf(true) }
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            ModalBottomSheet(
                onDismissRequest = { continueShopping() },
                windowInsets = windowInsets,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
                containerColor = Color.White,
                dragHandle = null,
            ) {
                AddedCartBottomSheetCard(
                    productTitle = bottomSheetState.productTitle,
                    productPrice = bottomSheetState.totalCartPrice,
                    isAdded = bottomSheetState.isAdded,
                    isTotalPriceAdded = bottomSheetState.isTotalPriceLoaded,
                    continueShopping = { continueShopping() },
                    viewCart = { viewCart() }
                )
            }
        }

    }
}


@Preview
@Composable
private fun WishListScreenContentPreview() {
//    WishListScreenContent(
//        productState = listOf(
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
//        ),
//        back = {},
//        navigateToProductDetails = {},
//        deleteProduct = {},
//        addToCart = {}
//    )
}
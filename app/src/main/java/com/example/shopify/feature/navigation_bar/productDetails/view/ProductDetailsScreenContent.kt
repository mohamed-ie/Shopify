package com.example.shopify.feature.navigation_bar.productDetails.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
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
import com.example.shopify.feature.navigation_bar.productDetails.components.AddedCartBottomSheetCard
import com.example.shopify.feature.navigation_bar.productDetails.components.OverViewCard
import com.example.shopify.feature.navigation_bar.productDetails.components.ProductDetailsCard
import com.example.shopify.feature.navigation_bar.productDetails.components.ProductSnackBar
import com.example.shopify.feature.navigation_bar.productDetails.components.ProductTopBar
import com.example.shopify.feature.navigation_bar.productDetails.components.VariantSection
import com.example.shopify.feature.navigation_bar.productDetails.components.VendorCard
import com.example.shopify.feature.navigation_bar.productDetails.model.Discount
import com.example.shopify.feature.navigation_bar.productDetails.model.Price
import com.example.shopify.feature.navigation_bar.productDetails.model.Product
import com.example.shopify.feature.navigation_bar.productDetails.model.VariantItem
import com.example.shopify.theme.shopifyColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    product: Product,
    addToCardState: AddToCardState,
    variantsState: VariantsState,
    viewCart:()->Unit,
    back:()->Unit
) {
    Scaffold(
        topBar = { ProductTopBar{back()} },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.shopifyColors.ServerColor)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                ProductDetailsCard(
                    vendor = product.vendor,
                    title = product.title,
                    thumbnail = product.image,
                    currencyCode = product.price.currencyCode,
                    price = product.price.amount,
                    realPrice = product.discount.realPrice,
                    discountPercent = product.discount.percent.toString(),
                    quantity = product.totalInventory.toString(),
                    isLowStock = variantsState.isLowStock
                )

                Spacer(modifier = Modifier.height(15.dp))

                VariantSection(
                    variants = variantsState.variants,
                    selected = variantsState.selectedVariant,
                    isChangingQuantity = false,
                    quantitySelected = {selectedVariant -> variantsState.selectVariant(selectedVariant)}
                )

                Card(
                    modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    VendorCard(name = product.vendor)
                }

                OverViewCard(
                    description = product.description
                )
                Spacer(modifier = Modifier.height(15.dp))


            }
            ProductSnackBar(
                opened = addToCardState.isOpened,
                selected = addToCardState.selectedQuantity,
                availableQuantity = addToCardState.availableQuantity,
                isChangingQuantity = false,
                quantitySelected = {selectedQuantity -> addToCardState.sendSelectedQuantity(selectedQuantity) },
                openQuantity = {addToCardState.openQuantity()},
                closeQuantity = {addToCardState.closeQuantity()},
                addToCard = {addToCardState.addToCard()}
            )
        }
        if (addToCardState.expandBottomSheet) {
            val edgeToEdgeEnabled by remember { mutableStateOf(true) }
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            ModalBottomSheet(
                onDismissRequest = { /*TODO*/ },
                windowInsets = windowInsets,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
                containerColor = Color.White,
                dragHandle = null,
            ) {
                AddedCartBottomSheetCard(
                    productTitle = product.title,
                    productPrice = addToCardState.totalCartPrice,
                    isAdded = addToCardState.isAdded,
                    continueShopping = {addToCardState.continueShopping()},
                    viewCart = {viewCart()}
                )
            }
        }

    }
}




@Preview
@Composable
private fun ProductDetailsScreenContentPreview(){
    ProductDetailsScreenContent(
        Product(
            image = "https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800",
            isGiftCard = false,
            description = "The Stan Smith owned the tennis court in the '70s." +
                    " Today it runs the streets with the same clean," +
                    " classic style." +
                    " These kids' shoes preserve the iconic look of the original," +
                    " made in leather with punched 3-Stripes," +
                    " heel and tongue logos and lightweight step-in cushioning.",
            totalInventory = 5,
            variants = listOf(VariantItem("","","","white/1")),
            title = "Ultima show Running Shoes Pink",
            requiresSellingPlan = false,
            tags = listOf(),
            createdAt = "",
            vendor = "Adidas",
            onlineStoreUrl = "",
            productType = "",
            price = Price(
                amount = "172.00",
                currencyCode = "AED"
            ),
            discount = Discount(
                realPrice = "249.00",
                percent = 30
            )
        ),
        addToCardState = AddToCardState(sendSelectedQuantity = {i ->}, openQuantity = {}, closeQuantity = {}, addToCard = {}, continueShopping = {}, availableQuantity = 5),
        variantsState = VariantsState(selectVariant = {}, isLowStock = true),
        viewCart = {},
        back = {}
    )
}






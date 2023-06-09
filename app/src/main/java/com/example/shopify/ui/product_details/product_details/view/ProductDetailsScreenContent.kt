package com.example.shopify.ui.product_details.product_details.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.common.component.ShopifyOutlinedButton
import com.example.shopify.model.product_details.Discount
import com.example.shopify.model.product_details.Price
import com.example.shopify.model.product_details.Product
import com.example.shopify.model.product_details.VariantItem
import com.example.shopify.ui.product_details.product_details.components.AddedCartBottomSheetCard
import com.example.shopify.ui.product_details.product_details.components.OverViewCard
import com.example.shopify.ui.product_details.product_details.components.ProductDetailsCard
import com.example.shopify.ui.product_details.product_details.components.ProductSnackBar
import com.example.shopify.ui.product_details.product_details.components.ProductTopBar
import com.example.shopify.ui.product_details.product_details.components.ReviewsSection
import com.example.shopify.ui.product_details.product_details.components.VariantSection
import com.example.shopify.ui.product_details.product_details.components.VendorCard
import com.example.shopify.ui.theme.shopifyColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreenContent(
    product: Product,
    addToCardState: AddToCardState,
    variantsState: VariantsState,
    reviewsState: ReviewsState,
    viewReviewsMore: () -> Unit,
    viewCart: () -> Unit,
    navigateToAuth: () -> Unit,
    onFavouriteClick: (Boolean) -> Unit,
    back: () -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            ProductTopBar(
                navigateToSearch = navigateToSearch,
                back = back
            )
        },
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
                    thumbnails = product.images,
                    currencyCode = product.price.currencyCode,
                    price = product.price.amount,
                    quantity = product.totalInventory.toString(),
                    isLowStock = variantsState.isLowStock,
                    isFavourite = product.isFavourite,
                    onFavouriteClick = onFavouriteClick,
                    isAvailable = variantsState.isAvailable
                )

                Spacer(modifier = Modifier.height(15.dp))

                VariantSection(
                    variants = variantsState.variants,
                    selected = variantsState.selectedVariant,
                    isChangingQuantity = false,
                    quantitySelected = { selectedVariant ->
                        variantsState.selectVariant(
                            selectedVariant
                        )
                    }
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
                if (reviewsState.reviewCount != 0)
                    ReviewsSection(
                        reviewsState = reviewsState,
                        divider = {},
                        lastSection = {
                            ShopifyOutlinedButton(
                                onClick = { viewReviewsMore() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                                border = BorderStroke(
                                    width = .8.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                            ) {
                                Text(
                                    text = stringResource(R.string.view_more),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                )
                            }
                        }
                    )
                Spacer(modifier = Modifier.height(15.dp))

            }
            if (variantsState.isAvailable)
                ProductSnackBar(
                    opened = addToCardState.isOpened,
                    selected = addToCardState.selectedQuantity,
                    availableQuantity = addToCardState.availableQuantity,
                    isChangingQuantity = false,
                    quantitySelected = { selectedQuantity ->
                        addToCardState.sendSelectedQuantity(
                            selectedQuantity
                        )
                    },
                    openQuantity = { addToCardState.openQuantity() },
                    closeQuantity = { addToCardState.closeQuantity() },
                    addToCard = {
                        if (product.isLogged)
                            addToCardState.addToCard()
                        else
                            navigateToAuth()
                    }
                )
        }
        if (addToCardState.expandBottomSheet) {
            val edgeToEdgeEnabled by remember { mutableStateOf(true) }
            val windowInsets = if (edgeToEdgeEnabled)
                WindowInsets(0) else BottomSheetDefaults.windowInsets
            ModalBottomSheet(
                onDismissRequest = { addToCardState.continueShopping() },
                windowInsets = windowInsets,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
                containerColor = Color.White,
                dragHandle = null,
            ) {
                AddedCartBottomSheetCard(
                    productTitle = product.title,
                    productPrice = addToCardState.totalCartPrice,
                    isAdded = addToCardState.isAdded,
                    isTotalPriceAdded = addToCardState.isTotalPriceLoaded,
                    continueShopping = { addToCardState.continueShopping() },
                    viewCart = { viewCart() }
                )
            }
        }

    }
}


@Preview
@Composable
private fun ProductDetailsScreenContentPreview() {
    ProductDetailsScreenContent(
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
        ),
        addToCardState = AddToCardState(
            sendSelectedQuantity = { i -> },
            openQuantity = {},
            closeQuantity = {},
            addToCard = {},
            continueShopping = {},
            availableQuantity = 5
        ),
        variantsState = VariantsState(selectVariant = {}, isLowStock = true),
        reviewsState = ReviewsState(
            averageRating = 3.5,
            reviewCount = 66,
            ratingCount = 20,
            reviews = listOf(
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                )
            )
        ),
        viewReviewsMore = {},
        viewCart = {},
        back = {},
        onFavouriteClick = {},
        navigateToSearch = {},
        navigateToAuth = {}
    )
}






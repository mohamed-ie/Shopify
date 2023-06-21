package com.example.shopify.ui.product_details.reviews.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.model.product_details.Discount
import com.example.shopify.model.product_details.Price
import com.example.shopify.model.product_details.Product
import com.example.shopify.model.product_details.VariantItem
import com.example.shopify.ui.product_details.product_details.components.ReviewsSection
import com.example.shopify.ui.product_details.product_details.view.Review
import com.example.shopify.ui.product_details.product_details.view.ReviewsState
import com.example.shopify.ui.product_details.reviews.view.components.ReviewTopBar
import com.example.shopify.ui.theme.shopifyColors


@Composable
fun ReviewDetailsScreenContent(
    reviewsState: ReviewsState,
    product: Product,
    back:()->Unit
) {
    Scaffold(
        topBar = { ReviewTopBar(
            product =product,
            back = back
        ) }
    ) {paddingValue ->
        Column (modifier = Modifier
            .padding(paddingValue).padding(bottom = 20.dp)
            .verticalScroll(rememberScrollState())){
            ReviewsSection(
                reviewsState = reviewsState,
                divider = { Divider(color =  MaterialTheme.shopifyColors.ServerColor, thickness = 15.dp) }
            )
        }
    }
}


@Preview
@Composable
fun ReviewDetailsContentPreview() {
    ReviewDetailsScreenContent(
        reviewsState = ReviewsState(averageRating = 3.5,
            reviewCount = 66,
            ratingCount = 20,
            reviews = listOf(
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                ),
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                ),
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                ),
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                ),
                Review(
                    reviewer = "",
                    rate = 2.5,
                    review = "Perfect phone",
                    description = "Its very nice experience Ga iloved it",
                    time = "2 months ago"
                )
            )),
        product = Product(
            images = listOf("https://www.skechers.com/dw/image/v2/BDCN_PRD/on/demandware.static/-/Sites-skechers-master/default/dw5fb9d39e/images/large/149710_MVE.jpg?sw=800"),
            description = "The Stan Smith owned the tennis court in the '70s." +
                    " Today it runs the streets with the same clean," +
                    " classic style." +
                    " These kids' shoes preserve the iconic look of the original," +
                    " made in leather with punched 3-Stripes," +
                    " heel and tongue logos and lightweight step-in cushioning.",
            totalInventory = 5,
            variants = listOf(VariantItem("","","white/1",0)),
            title = "iPhone 14 Pro 256GB Deep Purple 5G With FaceTime - International Version",
            price = Price(
                amount = "172.00",
                currencyCode = "AED"
            ),
            discount = Discount(
                realPrice = "249.00",
                percent = 30
            ),
            vendor = "Adidas",
        ) ) {

    }
}
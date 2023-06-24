package com.example.shopify.ui.order.review

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.common.component.RatingBar
import com.example.shopify.ui.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading


@Composable
fun ReviewInputBottomSheet(
    reviewState: ReviewState,
    onSubmitReviewClick:() -> Unit,
    onReviewContentChange:(String)->Unit,
    onReviewTitleChange:(String)->Unit,
    onRatingSelected:(Int) -> Unit,
    onCloseBottomSheet:() -> Unit
) {
    Column(
        modifier = Modifier.background(Color.Gray.copy(alpha = .6f)).fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 20.dp)
                .offset(y = 16.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(10.dp)
                .clickable {
                    onCloseBottomSheet()
                }


        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            Text(
                text = stringResource(R.string.write_a_product_review),
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Divider(color = Color.LightGray, thickness = 1.dp)
            Text(
                text = stringResource(R.string.add_a_title),
                modifier = Modifier.padding(12.dp)
            )
            OutlinedTextField(
                value = reviewState.title,
                onValueChange = onReviewTitleChange,
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.what_would_you_like_to_highlight),
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelMedium
                    )
                },

                )
            Text(
                text = stringResource(R.string.add_a_review),
                modifier = Modifier.padding(12.dp)
            )
            OutlinedTextField(
                value = reviewState.reviewContent,
                onValueChange = onReviewContentChange,
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.reivew_place_holder),
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )

            Text(
                text = stringResource(R.string.add_rating),
                modifier = Modifier.padding(12.dp)
            )

            RatingBar(
                rating = reviewState.rating,
                filedStarsColor = MaterialTheme.shopifyColors.Orange,
                modifier = Modifier.padding(horizontal = 12.dp),
                selectedReview = onRatingSelected
            )

            Button(
                onClick = onSubmitReviewClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 20.dp)
                    .height(50.dp),
                enabled = !reviewState.isLoading,
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = stringResource(R.string.submit_review),
                    modifier = Modifier.shopifyLoading(reviewState.isLoading, MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}


@Preview
@Composable
fun ReviewInputBottomSheetReview() {
    //ReviewInputBottomSheet()
}
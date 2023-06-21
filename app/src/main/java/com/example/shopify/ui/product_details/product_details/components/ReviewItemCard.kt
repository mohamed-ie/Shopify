package com.example.shopify.ui.product_details.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.common.component.RatingBar
import com.example.shopify.ui.product_details.product_details.view.Review
import com.example.shopify.ui.theme.shopifyColors


@Composable
fun ReviewItemCard(
    review: Review
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = review.reviewer,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 3.dp)
                )
                RatingBar(
                    rating = review.rate,
                    filedStarsColor = MaterialTheme.shopifyColors.DarkGreenColor
                )
            }
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.shopifyColors.MediumBlue,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text =  stringResource(R.string.verified_purchase),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text =  review.time ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text =  review.review,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 3.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text =  review.description,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(start = 3.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Divider(color =  MaterialTheme.shopifyColors.ServerColor, thickness = 1.dp)

    }

}


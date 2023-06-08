package com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.shopify.feature.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.model.Price
import com.example.shopify.theme.shopifyColors
import com.example.shopify.utils.shopifyLoading

@Composable
fun AddedCartBottomSheetCard(
    productTitle:String,
    productPrice: Price,
    isAdded:Boolean,
    continueShopping:()->Unit,
    viewCart:()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 30.dp, horizontal = 20.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.shopifyColors.DarkGreenColor,
                modifier = Modifier
                    .size(37.dp)
                    .shopifyLoading(!isAdded)
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = productTitle,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
                Text(
                    text = stringResource(R.string.added_to_cart),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.shopifyColors.DarkGreenColor,
                    modifier = Modifier.shopifyLoading(!isAdded),
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(7.dp))
                .background(MaterialTheme.shopifyColors.ServerColor)
                .padding(vertical = 9.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cart_total),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
            Text(
                text =  productPrice.currencyCode + " " + productPrice.amount,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        ShopifyOutlinedButton(
            onClick = { continueShopping() },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(width = .8.dp, color = MaterialTheme.colorScheme.primary)

        ) {
            Text(
                text = stringResource(R.string.continue_shopping),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { viewCart() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.view_cart),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

    }
}

@Preview
@Composable
fun AddedCartBottomSheetPreview() {
    AddedCartBottomSheetCard(
        productTitle = "iPhone 14 Pro 256GB Deep Purple 5G Wit",
        Price("4,199.00","AED"),
        isAdded = false,
        continueShopping = {},
        viewCart = {}
    )
}

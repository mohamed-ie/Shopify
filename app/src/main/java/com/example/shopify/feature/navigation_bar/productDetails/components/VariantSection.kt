package com.example.shopify.feature.navigation_bar.productDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Straighten
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
import com.example.shopify.feature.common.component.ShopifyOutlinedButton
import com.example.shopify.feature.common.component.ShopifyOutlinedButtonState
import com.example.shopify.feature.navigation_bar.productDetails.model.VariantItem
import com.example.shopify.utils.shopifyLoading

@Composable
fun VariantSection(
    variants:List<VariantItem>,
    selected: Int,
    isChangingQuantity: Boolean,
    quantitySelected: (Int) -> Unit
) {
    val quantityListState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(R.string.variant),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Straighten,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.size_guide),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(state = quantityListState, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(variants.count()) {
                ShopifyOutlinedButton(
                    shape = RoundedCornerShape(10.dp),
                    onClick = { quantitySelected(it + 1) },
                    state = if ((it + 1) == selected)
                        ShopifyOutlinedButtonState.Selected
                    else
                        ShopifyOutlinedButtonState.Normal
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .shopifyLoading(enabled = isChangingQuantity),
                        text = variants[it].title ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
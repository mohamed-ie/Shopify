package com.example.shopify.ui.bottom_bar.home.product.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.bottom_bar.home.product.model.BrandProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: BrandProduct,
    onProductItemClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onProductItemClick
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImageCardScrollHorizontally(
                images = product.images,
                isFavourite = product.isFavourite,
                addToFavourite = onFavouriteClick
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                text = product.title,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 10.dp),
                verticalAlignment = Alignment.Bottom,

                ) {
                Text(
                    text = product.price.currencyCode.toString(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.price.amount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 15.dp
                    ),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 15.dp)
                        .height(20.dp),
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(bottom = 1.dp, start = 5.dp, end = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontStyle = FontStyle.Italic

                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}


package com.example.shopify.feature.navigation_bar.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.theme.ShopifyTheme

@Composable
fun RemoteErrorHeader(error: String?) {
    AnimatedVisibility(
        visible = !error.isNullOrBlank(),
        enter = expandVertically(expandFrom = Alignment.Top, animationSpec = tween(1500)),
        exit = shrinkVertically(shrinkTowards = Alignment.Top, animationSpec = tween(1500))
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.errorContainer)
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(ButtonDefaults.IconSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "!",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                text = error?:"",
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun RemoteErrorHeaderSmallText() {
    ShopifyTheme {
        RemoteErrorHeader("Help")
    }
}

@Preview
@Composable
fun RemoteErrorHeaderLargeText() {
    ShopifyTheme {
        RemoteErrorHeader("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)")
    }
}
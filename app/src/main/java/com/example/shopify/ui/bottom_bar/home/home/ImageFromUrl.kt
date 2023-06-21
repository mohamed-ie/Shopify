package com.example.shopify.ui.bottom_bar.home.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.example.shopify.utils.shopifyLoading

@Composable
fun ImageFromUrl(url: String?) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f),
        model = url,
        contentScale = ContentScale.Fit,
        contentDescription = null,
        loading = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .shopifyLoading())
        },
        error = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Rounded.BrokenImage,
                tint = Color.Gray,
                contentDescription = null
            )
        })
}

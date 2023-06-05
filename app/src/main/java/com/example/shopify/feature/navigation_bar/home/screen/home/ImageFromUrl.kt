package com.example.shopify.feature.navigation_bar.home.screen.home

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
    /*
* SubcomposeAsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                model = product.thumbnail,
                contentDescription = null,
                loading = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .shopifyLoading()
                    )
                },
                error = {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.BrokenImage,
                        tint = Color.Gray,
                        contentDescription = null
                    )
                }
            ) */
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


/* val painter = rememberImagePainter(
     data = url

 )
 Box(
     modifier = Modifier
         .size(width = 200.dp, height = 100.dp)
         .fillMaxSize()
         .background(Color.White)
 ) {
     Image(
         painter = painter,
         contentDescription = null,
         modifier = Modifier.fillMaxSize()
     )
 }
}
*/

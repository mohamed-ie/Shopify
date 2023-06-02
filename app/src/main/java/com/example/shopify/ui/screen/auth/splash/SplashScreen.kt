package com.example.shopify.ui.screen.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R


@Composable
fun SplashScreen() {
    Scaffold {
        Box(
            modifier = Modifier.padding(it).fillMaxSize().padding(end = 10.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.splash_screen_logo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.size(350.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreViewSplashScreen() {
    SplashScreen()
}
package com.example.shopify.ui.landing.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navigateToHome: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        delay(1000L)
        navigateToHome()
    }
    SplashScreenContent()
}


@Composable
fun SplashScreenContent() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen_logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp)
        )
    }
}


@Preview
@Composable
fun PreViewSplashScreen() {
    SplashScreenContent()
}
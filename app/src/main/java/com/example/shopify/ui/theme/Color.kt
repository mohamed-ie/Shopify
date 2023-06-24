package com.example.shopify.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Gray = Color.Gray
val Green170 = Color(0xFF64DD17)
val ShopifyBorderStrokeColor = Color.Gray.copy(.5f)



object ShopifyColors {
    val Black = Color(0xFF33363D)
    val LightServer = Color(0xFFF9F9F9)
    val Gray = Color(0xFF5B6E81)
    val LightGray = Color(0x9E5B6E81)
    val Silver = Color(0xFFEFF3FD)
    val ServerColor = Color(0xFFEAEBF4)
    val DarkGreenColor = Color(0xFF00AA00)
    val Orange = Color(0xFFF3AB31)
    val MediumBlue = Color(0xFF3866DF)
}

val  MaterialTheme.shopifyColors: ShopifyColors
    get() = ShopifyColors


package com.example.shopify.ui.common.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shopify.ui.theme.shopifyColors

@Composable
fun HomeTopBar(
    back: () -> Unit,
    content: @Composable () -> Unit = {},
){
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        NamedTopAppBar(back = back)
        content()
        Divider(color =  MaterialTheme.shopifyColors.ServerColor, thickness = 1.dp)
    }
}
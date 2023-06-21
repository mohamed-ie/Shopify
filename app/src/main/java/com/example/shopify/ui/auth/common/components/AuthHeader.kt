package com.example.shopify.ui.auth.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R


@Composable
fun AuthHeader(
    onCloseScreen:()->Unit
) {
    Row (modifier = Modifier.fillMaxWidth()){
        Image(painter = painterResource(id = R.drawable.shopfity_logo),
            colorFilter = ColorFilter.tint(Color.DarkGray),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(50.dp))
        Text(text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier.padding(start = 4.dp, top = 23.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Rounded.Close,
            modifier = Modifier.clickable { onCloseScreen() },
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}


@Preview
@Composable
fun AuthHeaderPreview() {
    AuthHeader({})
}
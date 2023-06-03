package com.example.shopify.ui.screen.auth.common

import androidx.compose.foundation.background
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


@Composable
fun ErrorCard(
    error:String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ){
            Box(
                modifier = Modifier
                    .size(ButtonDefaults.IconSize)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "!",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                text = error,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun PreviewErrorCard() {
    ErrorCard("Invalid Password")
}
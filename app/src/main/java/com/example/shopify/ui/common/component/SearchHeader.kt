package com.example.shopify.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.ui.theme.shopifyColors

@Composable
fun SearchHeader(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 3.dp)
            .offset(y = (-7).dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.shopifyColors.ServerColor),
        shape = RoundedCornerShape(7.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.search_message),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
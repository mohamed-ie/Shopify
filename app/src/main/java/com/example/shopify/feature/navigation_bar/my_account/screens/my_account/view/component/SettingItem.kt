package com.example.shopify.feature.navigation_bar.my_account.screens.my_account.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun SettingItem(
    text: String,
    icon: ImageVector,
    current: @Composable () -> Unit = {},
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(role = Role.Button, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            tint = MaterialTheme.colorScheme.primary,
            imageVector = icon,
            contentDescription = null)

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))

        current()
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.ArrowRight,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
    }
}

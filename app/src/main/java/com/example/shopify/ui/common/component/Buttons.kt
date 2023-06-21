package com.example.shopify.ui.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.shopify.ui.theme.ShopifyBorderStrokeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopifyOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    state: ShopifyOutlinedButtonState = ShopifyOutlinedButtonState.Normal,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = state.containerColor(),
        contentColor = state.contentColor()
    ),
    elevation: ButtonElevation? = null,
    contentPadding: PaddingValues = PaddingValues(
        start = 10.dp,
        end = 10.dp,
        top = 6.dp,
        bottom = 6.dp
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    border: BorderStroke? = BorderStroke(width = .8.dp, color = state.borderColor()),
    content: @Composable RowScope.() -> Unit,
) =
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
    ) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            content = content
        )
    }


sealed class ShopifyOutlinedButtonState(
    val borderColor: @Composable () -> Color = { ShopifyBorderStrokeColor },
    val containerColor: @Composable () -> Color = { Color.Transparent },
    val contentColor: @Composable () -> Color = { Color.Black }
) {
    object Normal : ShopifyOutlinedButtonState()
    object Active : ShopifyOutlinedButtonState(borderColor = { Color.Black })

    object Selected : ShopifyOutlinedButtonState(
        borderColor = { MaterialTheme.colorScheme.primary },
        containerColor = { MaterialTheme.colorScheme.primary.copy(alpha = .2f) },
        contentColor = { MaterialTheme.colorScheme.primary }
    )
}

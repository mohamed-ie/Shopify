package com.example.shopify.feature.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    boxModifier:Modifier = Modifier,
    value:String,
    shape: Shape,
    colors:SearchTextFieldColors,
    onValueChange:(String)->Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: (@Composable () -> Unit),
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize
) {
    BasicTextField(modifier = modifier
        .background(
            colors.containerColor,
            shape = shape
        )
        .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        cursorBrush = SolidColor(colors.cursorColor),
        textStyle = LocalTextStyle.current.copy(
            color = colors.textColor,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                boxModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Spacer(modifier = Modifier.width(3.dp))
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty())
                        placeholderText()
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


data class SearchTextFieldColors(
    val containerColor:Color,
    val textColor:Color,
    val cursorColor: Color = Color.Gray,
)
package com.example.shopify.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopify.ui.theme.shopifyColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopifyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: ShopifyTextColors = ShopifyTextColors(),
    focusedIndicatorThinness: Dp = 1.dp,
    unfocusedIndicatorThinness: Dp = 1.dp,
) {
    var focused by remember { mutableStateOf(false) }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.onFocusChanged { focused = it.isFocused },
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        readOnly = readOnly,
        textStyle = textStyle,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource
    ) { innerTextField ->
        Column(Modifier.fillMaxWidth()) {
            label?.let { it() }
            Spacer(modifier = Modifier.height(4.dp))
            Box {
                if (value.isEmpty())
                    placeholder?.let { it() }

                innerTextField()
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (focused) focusedIndicatorThinness
                        else unfocusedIndicatorThinness
                    )
                    .background(
                        if (focused) colors.focusedIndicatorColor
                        else colors.unfocusedIndicatorColor
                    )
            )
            Spacer(modifier = Modifier.height(4.dp))
            supportingText?.let { it() }
        }
    }
}


data class ShopifyTextColors(
    val unfocusedContainerColor: Color = Color.Transparent,
    val focusedContainerColor: Color = Color.Transparent,
    val errorContainerColor: Color = Color.Transparent,
    val disabledIndicatorColor: Color = MaterialTheme.shopifyColors.LightGray,
    val focusedIndicatorColor: Color = MaterialTheme.shopifyColors.Black,
    val unfocusedIndicatorColor: Color = MaterialTheme.shopifyColors.LightGray
) {
    @Composable
    private fun toTextColors() =
        TextFieldDefaults.colors(
            focusedContainerColor = focusedContainerColor,
            unfocusedContainerColor = unfocusedContainerColor,
            errorContainerColor = errorContainerColor,
            focusedIndicatorColor = focusedIndicatorColor,
            unfocusedIndicatorColor = unfocusedIndicatorColor,
            disabledIndicatorColor = disabledIndicatorColor,
        )
}

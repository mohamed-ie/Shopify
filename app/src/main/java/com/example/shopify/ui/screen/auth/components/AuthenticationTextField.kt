package com.example.shopify.ui.screen.auth.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AuthTextField(
    text: String,
    @StringRes textFieldHeader: Int,
    @StringRes placeHolder: Int,
    modifier: Modifier,
    isError:Boolean,
    @StringRes errorMessage:Int,
    onTextChanged: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = textFieldHeader),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.padding(vertical = 7.dp))
        CustomTextFiled(text = text, placeHolder = placeHolder, isError, onTextChanged = onTextChanged)
        Spacer(modifier = Modifier.padding(vertical = 3.dp))
        if (isError)
            Text(text = stringResource(id = errorMessage),style = MaterialTheme.typography.labelMedium,
                color = Color.Red)
    }
}

@Composable
fun AuthPasswordTextField(
    text: String,
    @StringRes textFieldHeader: Int,
    @StringRes placeHolder: Int,
    modifier: Modifier,
    isError:Boolean,
    @StringRes errorMessage:Int,
    onTextChanged: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = textFieldHeader),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.padding(vertical = 7.dp))
        CustomPasswordTextFiled(text = text, placeHolder = placeHolder, isError, onTextChanged = onTextChanged)
        Spacer(modifier = Modifier.padding(vertical = 3.dp))
        if (isError)
            Text(text = stringResource(id = errorMessage),style = MaterialTheme.typography.labelMedium,
                color = Color.Red)


    }
}






@Composable
private fun CustomTextFiled(
    text: String,
    @StringRes placeHolder: Int,
    isError:Boolean,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier.fillMaxWidth(),
        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
        visualTransformation = VisualTransformation.None,
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = if (isError) Color.Red else Color.LightGray,
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx(),
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx(),
                        ),
                        strokeWidth = 2.dp.toPx(),
                    )
                },
            ) {
                Box {
                    if (text.isEmpty())
                        Text(
                            text = stringResource(id = placeHolder),
                            modifier = Modifier.padding(bottom = 5.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.LightGray
                        )

                    innerTextField()
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )

}

@Composable
private fun CustomPasswordTextFiled(
    text: String,
    @StringRes placeHolder: Int,
    isError:Boolean,
    onTextChanged: (String) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(true) }
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier.fillMaxWidth(),
        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
        visualTransformation = if (!passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = if (isError) Color.Red else Color.LightGray,
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx(),
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx(),
                        ),
                        strokeWidth = 2.dp.toPx(),
                    )
                },
            ) {
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Box {
                        if (text.isEmpty())
                            Text(
                                text = stringResource(id = placeHolder),
                                modifier = Modifier.padding(bottom = 5.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.LightGray
                            )

                        innerTextField()
                    }
                    IconButton(onClick = {
                                         passwordVisible = !passwordVisible
                    },modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(22.dp)) {
                        Icon(imageVector = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff, tint = Color.Gray, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )

}






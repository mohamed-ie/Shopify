package com.example.shopify.ui.screen.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.theme.ShopifyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarItem(
    onSearch: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        textStyle = TextStyle(color = Color.Black),
        placeholder = {
            Text(
                text = "Search",
                color = Color.Gray
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchQuery.value)
            }
        )
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    ShopifyTheme {
        SearchBarItem {}
    }
}
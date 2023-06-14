package com.example.shopify.feature.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.feature.navigation_bar.common.HomeTopBar
import com.example.shopify.theme.shopifyColors


@Composable
fun SearchTopBar(
    searchedText:String,
    onValueChange:(String) -> Unit,
    back:() -> Unit
) {
    HomeTopBar(
        back = back,
        content = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.shopifyColors.ServerColor)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                SearchTextField(
                    value = searchedText,
                    colors = SearchTextFieldColors(
                      containerColor = Color.White,
                      textColor = Color.Gray
                    ),
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(5.dp),
                    boxModifier = Modifier.padding(start = 7.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    placeholderText = {
                        Text(
                            text = stringResource(R.string.search_message),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                        )
                    }


                )
            }
        }
    )
}




@Preview
@Composable
private fun SearchTopBarPreview() {
    SearchTopBar(
        searchedText = "",
        back = {},
        onValueChange = {}
    )
}
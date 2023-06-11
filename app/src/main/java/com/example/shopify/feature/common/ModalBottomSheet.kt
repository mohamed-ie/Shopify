package com.example.shopify.feature.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.R
import com.example.shopify.theme.ShopifyTheme
import com.example.shopify.theme.shopifyColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RadioGroupModalBottomSheet(
    visible: Boolean,
    title: String,
    options: List<String>,
    selected: String,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit = {}
) {
    val currencyListState = rememberLazyListState()
    if (visible) {
        LaunchedEffect(key1 = Unit, block = {
            currencyListState.animateScrollToItem(options.indexOf(selected))
        })

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.shopifyColors.Gray
            )
            LazyColumn(modifier = Modifier.weight(1f), state = currencyListState) {
                itemsIndexed(options) { index, text ->
                    val selectedOption = selected == text
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = selectedOption,
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        RadioButton(
                            selected = selectedOption,
                            onClick = null
                        )
                    }
                    if (index < options.size - 1)
                        Divider()
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun PreviewRadioGroupModalBottomSheet() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = visible) {
        delay(5000)
        visible = visible.not()
    }
    ShopifyTheme {
        RadioGroupModalBottomSheet(
            visible,
            stringResource(id = R.string.select_currency),
            listOf("EGP", "USD"),
            "EGP",
            {},
            {}
        )
    }
}
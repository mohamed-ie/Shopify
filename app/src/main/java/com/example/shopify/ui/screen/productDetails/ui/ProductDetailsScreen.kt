package com.example.shopify.ui.screen.productDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopify.R
import com.example.shopify.ui.screen.common.NamedTopAppBar
import com.example.shopify.ui.theme.SearchBarColor


@Composable
fun ProductDetailsScreenContent() {
    Scaffold(
        topBar = { ProductTopBar() {} },
        snackbarHost = {

        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            
        }
    }
}

@Composable
private fun ProductTopBar(back:() -> Unit){
    Column(
        modifier = Modifier.background(Color.White),

    ) {
        NamedTopAppBar(back = back)
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 3.dp)
                .offset(y = (-7).dp),
            colors = ButtonDefaults.buttonColors(containerColor = SearchBarColor),
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
        Divider(color = SearchBarColor, thickness = 1.dp)

    }
}

@Composable
fun ProductSnackBar() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 7.dp)
            .fillMaxWidth()

    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth().height(45.dp),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(
                text = "ADD TO CART",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//@Preview
//@Composable
//private fun ProductDetailsScreenContentPreview() {
//    ProductDetailsScreenContent()
//}

@Preview
@Composable
private fun ProductTopBarPreview() {
    ProductSnackBar()
}
package com.example.shopify.feature.navigation_bar.my_account.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopify.feature.navigation_bar.common.NamedTopAppBar
import com.example.shopify.feature.navigation_bar.my_account.MyAccountGraph
import com.example.shopify.feature.navigation_bar.my_account.screens.profile.component.ProfileHeader
import com.example.shopify.feature.navigation_bar.my_account.screens.profile.component.SecurityInformationCard
import com.example.shopify.theme.ShopifyTheme

@Composable
fun ProfileScreenContent(
    state: ProfileState,
    navigateTo: (String) -> Unit,
    back: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NamedTopAppBar(back = back)
        ProfileHeader(
            name = state.name,
            email = state.email,
            onEditInfoClick = { navigateTo(MyAccountGraph.EDIT_INFO) }
        )

        SecurityInformationCard(
            onChangePasswordClick = { navigateTo(MyAccountGraph.CHANGE_PASSWORD) },
            onChangePhoneNumber = { navigateTo(MyAccountGraph.CHANGE_PHONE_NUMBER) }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreenContent() {
    ShopifyTheme {
        ProfileScreenContent(ProfileState("mohamed ibrahim", "mohammedie98@gmail.com"), {},{})
    }
}
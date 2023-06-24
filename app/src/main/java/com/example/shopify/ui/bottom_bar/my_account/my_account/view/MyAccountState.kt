package com.example.shopify.ui.bottom_bar.my_account.my_account.view

data class MyAccountState(
    val name: String = "",
    val email: String = "",
    val currency: String = "EGP",
    val isSignOutDialogVisible: Boolean = false,
    val isRadioGroupModalBottomSheetVisible: Boolean = false
)
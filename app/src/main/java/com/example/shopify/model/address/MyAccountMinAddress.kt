package com.example.shopify.model.address

import com.example.shopify.R
import com.example.shopify.helpers.UIText

class MyAccountMinAddress(
    val id: String? = null,
    val name: UIText = UIText.StringResource(R.string.not_selected),
    val address:UIText = UIText.StringResource(R.string.not_selected),
    val phone: UIText = UIText.StringResource(R.string.not_selected),
    val isDefault: Boolean =false
)
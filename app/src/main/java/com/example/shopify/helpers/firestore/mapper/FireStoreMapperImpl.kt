package com.example.shopify.helpers.firestore.mapper

import com.example.shopify.feature.navigation_bar.model.remote.FireStoreManagerImpl
import com.example.shopify.feature.navigation_bar.productDetails.screens.productDetails.view.Review
import com.example.shopify.utils.Constants
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.shopify.graphql.support.ID
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class FireStoreMapperImpl @Inject constructor() : FireStoreMapper {

    override fun mapSnapShotDocumentsToReview(snapshot: DocumentSnapshot): Review =
        snapshot.data.let { snapShotMap ->
            Review(
                review = (snapShotMap?.get(FireStoreManagerImpl.Product.Field.REVIEW_CONTENT) as String),
                description = snapShotMap[FireStoreManagerImpl.Product.Field.DESCRIPTION_REVIEW] as String,
                reviewer = snapShotMap[FireStoreManagerImpl.Product.Field.REVIEWER_REVIEW] as String,
                rate = snapShotMap[FireStoreManagerImpl.Product.Field.RATE_REVIEW] as Double,
                time = SimpleDateFormat(
                    Constants.DateFormats.MONTH_DAY_PATTERN,
                    Locale.getDefault()
                ).format((snapShotMap[FireStoreManagerImpl.Product.Field.CREATED_AT_REVIEW] as Timestamp).toDate())
            )
        }


    override fun mapEncodedToDecodedProductId(encodedProductId:String):ID =
        encodedProductId.decodeProductId()


    override fun mapProductIDToEncodedProductId(productId:ID) =
        productId.encodeProductId()


}


fun String.decodeProductId():ID =
    ID(URLDecoder.decode(this, StandardCharsets.UTF_8.toString()))


fun ID.encodeProductId():String =
    URLEncoder.encode(this.toString(), StandardCharsets.UTF_8.toString())

package com.example.kakaoapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Document(
    val collection: String,
    val datetime: String,
    val display_sitename: String,
    val height: Int,
    val image_url: String,
    val width: Int
) : Parcelable
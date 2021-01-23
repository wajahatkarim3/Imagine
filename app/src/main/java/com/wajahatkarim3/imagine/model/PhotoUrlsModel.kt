package com.wajahatkarim3.imagine.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUrlsModel(
    @Expose val raw: String,
    @Expose val full: String,
    @Expose val regular: String,
    @Expose val small: String,
    @Expose val thumb: String
): Parcelable
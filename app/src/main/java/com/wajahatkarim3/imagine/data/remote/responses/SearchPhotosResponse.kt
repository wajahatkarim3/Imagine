package com.wajahatkarim3.imagine.data.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wajahatkarim3.imagine.model.PhotoModel

data class SearchPhotosResponse(
    @Expose @SerializedName("total") val total: Int,
    @Expose @SerializedName("total_pages") val totalPages: Int,
    @Expose @SerializedName("results") val photosList: List<PhotoModel>
)
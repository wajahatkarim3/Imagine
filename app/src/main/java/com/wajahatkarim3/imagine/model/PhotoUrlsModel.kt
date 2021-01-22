package com.wajahatkarim3.imagine.model

import com.google.gson.annotations.Expose

data class PhotoUrlsModel(
    @Expose val raw: String,
    @Expose val full: String,
    @Expose val regular: String,
    @Expose val small: String,
    @Expose val thumb: String
)
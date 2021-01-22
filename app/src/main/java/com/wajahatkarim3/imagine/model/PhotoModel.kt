package com.wajahatkarim3.imagine.model

import com.google.gson.annotations.Expose

data class PhotoModel (
    @Expose val id: String,
    @Expose val created_at: String,
    @Expose val color: String,
    @Expose val description: String,
    @Expose val urls: PhotoUrlsModel,
    @Expose val user: UserModel
)
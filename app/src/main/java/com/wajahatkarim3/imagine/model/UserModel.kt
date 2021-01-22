package com.wajahatkarim3.imagine.model

import com.google.gson.annotations.Expose

data class UserModel(
    @Expose val id: String,
    @Expose val username: String,
    @Expose val name: String
//    @Expose val profile_image: String
)
package com.wajahatkarim3.imagine.data.remote

import com.wajahatkarim3.imagine.model.PhotoModel
import com.wajahatkarim3.imagine.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("photos")
    suspend fun loadPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") numOfPhotos: Int = 10,
        @Query("order_by") orderBy: String = "popular",
        @Query("Authorization") authorization: String = AppConstants.API.API_KEY,
    ): ApiResponse<List<PhotoModel>>

    companion object {
        const val BASE_API_URL = "https://api.unsplash.com/"
    }
}
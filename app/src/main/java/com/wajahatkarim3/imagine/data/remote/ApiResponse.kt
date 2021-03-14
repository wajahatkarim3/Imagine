/*
* Copyright 2021 Wajahat Karim (https://wajahatkarim.com)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.wajahatkarim3.imagine.data.remote

import retrofit2.Response

/**
 * Common class used by API responses.
 */
sealed class ApiResponse<out T> {

    /**
     * API Success response class from retrofit.
     *
     * [data] is optional. (There are responses without data)
     */
    data class ApiSuccessResponse<T>(val response: Response<T>) : ApiResponse<T>() {
        val data: T? = response.body()
    }

    /**
     * Failure class represents two types of Failure:
     * 1) ### Error response e.g. server error
     * 2) ### Exception response e.g. network connection error
     */
    sealed class ApiFailureResponse<T> {
        data class Error<T>(val response: Response<T>) : ApiResponse<T>()

        data class Exception<T>(val exception: Throwable) : ApiResponse<T>() {
            val message: String? = exception.localizedMessage
        }
    }

    companion object {

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function. Only receives [Throwable] as an argument.
         */
        fun <T> exception(ex: Throwable) = ApiFailureResponse.Exception<T>(ex)

        /**
         * ApiResponse error Factory.
         *
         * [ApiFailureResponse] factory function.
         */
        fun <T> error(response: Response<T>) = ApiFailureResponse.Error<T>(response)

        /**
         * ApiResponse Factory.
         *
         * [create] Create [ApiResponse] from [retrofit2.Response] returning from the block.
         * If [retrofit2.Response] has no errors, it creates [ApiResponse.ApiSuccessResponse].
         * If [retrofit2.Response] has errors, it creates [ApiResponse.ApiFailureResponse.Error].
         * If [retrofit2.Response] has occurred exceptions, it creates [ApiResponse.ApiFailureResponse.Exception].
         */
        fun <T> create(
            successCodeRange: IntRange = 200..299,
            response: Response<T>
        ): ApiResponse<T> = try {
            if (response.raw().code in successCodeRange) {
                ApiSuccessResponse(response)
            } else {
                ApiFailureResponse.Error(response)
            }
        } catch (ex: Exception) {
            ApiFailureResponse.Exception(ex)
        }
    }
}

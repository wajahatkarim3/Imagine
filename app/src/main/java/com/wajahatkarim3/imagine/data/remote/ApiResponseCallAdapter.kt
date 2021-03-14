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

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A Retrofit adapter that converts the Call into a ApiResponse
 */
class ApiResponseCallAdapter constructor(
    private val responseType: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCall(call)
    }

    internal class ApiResponseCall(private val call: Call<Type>) : Call<ApiResponse<Type>> {
        override fun enqueue(callback: Callback<ApiResponse<Type>>) {
            call.enqueue(object : Callback<Type> {
                override fun onResponse(call: Call<Type>, response: Response<Type>) {
                    val apiResponse = ApiResponse.create(response = response)
                    callback.onResponse(this@ApiResponseCall, Response.success(apiResponse))
                }

                override fun onFailure(call: Call<Type>, t: Throwable) {
                    callback.onResponse(
                        this@ApiResponseCall, Response.success(ApiResponse.exception(t))
                    )
                }
            })
        }

        override fun cancel() = call.cancel()
        override fun request(): Request = call.request()
        override fun isExecuted() = call.isExecuted
        override fun isCanceled() = call.isCanceled
        override fun execute(): Response<ApiResponse<Type>> =
            throw UnsupportedOperationException("ApiResponseCallAdapter doesn't support execute")

        override fun timeout(): Timeout = Timeout.NONE
        override fun clone(): Call<ApiResponse<Type>> = this
    }
}

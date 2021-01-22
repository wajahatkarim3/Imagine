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
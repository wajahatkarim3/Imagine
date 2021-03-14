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

/**
 * A suspend function for handling success response.
 */
@SuspensionFunction
suspend fun <T> ApiResponse<T>.onSuccessSuspend(
    onResult: suspend ApiResponse.ApiSuccessResponse<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiSuccessResponse) {
        onResult(this)
    }
    return this
}

/**
 * A suspend function for handling error response.
 */
@SuspensionFunction
suspend fun <T> ApiResponse<T>.onErrorSuspend(
    onResult: suspend ApiResponse.ApiFailureResponse.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiFailureResponse.Error) {
        onResult(this)
    }
    return this
}

/**
 * A suspend function for handling exception response.
 */
@SuspensionFunction
suspend fun <T> ApiResponse<T>.onExceptionSuspend(
    onResult: suspend ApiResponse.ApiFailureResponse.Exception<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiFailureResponse.Exception) {
        onResult(this)
    }
    return this
}

/** A message from the [ApiResponse.ApiFailureResponse.Error]. */
fun <T> ApiResponse.ApiFailureResponse.Error<T>.message(): String = toString()

/** A message from the [ApiResponse.ApiFailureResponse.Exception]. */
fun <T> ApiResponse.ApiFailureResponse.Exception<T>.message(): String = toString()

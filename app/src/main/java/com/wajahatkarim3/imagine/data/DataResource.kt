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
package com.wajahatkarim3.imagine.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class DataResource<out T>(
    val status: DataStatus,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): DataResource<T> {
            return DataResource(DataStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): DataResource<T> {
            return DataResource(DataStatus.ERROR, data, msg)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class DataStatus {
    SUCCESS,
    ERROR
}

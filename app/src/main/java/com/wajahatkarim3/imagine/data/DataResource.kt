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

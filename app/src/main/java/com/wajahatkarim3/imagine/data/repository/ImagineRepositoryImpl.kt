package com.wajahatkarim3.imagine.data.repository

import androidx.annotation.WorkerThread
import com.wajahatkarim3.imagine.data.DataResource
import com.wajahatkarim3.imagine.data.DataState
import com.wajahatkarim3.imagine.data.remote.*
import com.wajahatkarim3.imagine.model.PhotoModel
import com.wajahatkarim3.imagine.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [ImagineRepository] to handle communication with [UnsplashApiService] server.
 * @author Wajahat Karim
 */
class ImagineRepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: UnsplashApiService
): ImagineRepository {

    @WorkerThread
    override suspend fun loadPhotos(
        pageNumber: Int,
        pageSize: Int,
        orderBy: String
    ): Flow<DataState<List<PhotoModel>>> {
        return flow {
            apiService.loadPhotos(pageNumber, pageSize, orderBy).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<List<PhotoModel>>(message()))

                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<List<PhotoModel>>(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error<List<PhotoModel>>(stringUtils.somethingWentWrong()))
                }
            }
        }
    }

    override suspend fun searchPhotos(
        query: String,
        pageNumber: Int,
        pageSize: Int
    ): Flow<DataState<List<PhotoModel>>> {
        return flow {
            apiService.searchPhotos(query, pageNumber, pageSize).apply {
                this.onSuccessSuspend {
                    data?.let {
                        emit(DataState.success(it.photosList))
                    }
                }
                // handle the case when the API request gets an error response.
                // e.g. internal server error.
            }.onErrorSuspend {
                emit(DataState.error<List<PhotoModel>>(message()))

                // handle the case when the API request gets an exception response.
                // e.g. network connection error.
            }.onExceptionSuspend {
                if (this.exception is IOException) {
                    emit(DataState.error<List<PhotoModel>>(stringUtils.noNetworkErrorMessage()))
                } else {
                    emit(DataState.error<List<PhotoModel>>(stringUtils.somethingWentWrong()))
                }
            }
        }
    }
}
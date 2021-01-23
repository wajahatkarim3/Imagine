package com.wajahatkarim3.imagine.data.repository

import com.wajahatkarim3.imagine.data.DataResource
import com.wajahatkarim3.imagine.data.DataState
import com.wajahatkarim3.imagine.model.PhotoModel
import kotlinx.coroutines.flow.Flow

/**
 * ImagineRepository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [ImagineRepositoryImpl] for implementation of this class to utilize Unsplash API.
 * @author Wajahat Karim
 */
interface ImagineRepository {
    suspend fun loadPhotos(pageNumber: Int, pageSize: Int, orderBy: String): Flow<DataState<List<PhotoModel>>>
    suspend fun searchPhotos(query: String, pageNumber: Int, pageSize: Int): Flow<DataState<List<PhotoModel>>>
}
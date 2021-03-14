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
package com.wajahatkarim3.imagine.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wajahatkarim3.imagine.data.DataState
import com.wajahatkarim3.imagine.data.usecases.FetchPopularPhotosUsecase
import com.wajahatkarim3.imagine.data.usecases.SearchPhotosUsecase
import com.wajahatkarim3.imagine.model.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPopularPhotosUsecase: FetchPopularPhotosUsecase,
    private val searchPhotosUsecase: SearchPhotosUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<HomeUiState>()
    var uiStateLiveData: LiveData<HomeUiState> = _uiState

    private var _photosList = MutableLiveData<List<PhotoModel>>()
    var photosListLiveData: LiveData<List<PhotoModel>> = _photosList

    private var pageNumber = 1
    private var searchQuery: String = ""

    init {
        fetchPhotos(pageNumber)
    }

    fun loadMorePhotos() {
        pageNumber++
        if (searchQuery == "")
            fetchPhotos(pageNumber)
        else
            searchPhotos(searchQuery, pageNumber)
    }

    fun retry() {
        if (searchQuery == "")
            fetchPhotos(pageNumber)
        else
            searchPhotos(searchQuery, pageNumber)
    }

    fun searchPhotos(query: String) {
        searchQuery = query
        pageNumber = 1
        searchPhotos(query, pageNumber)
    }

    fun fetchPhotos(page: Int) {
        _uiState.postValue(if (page == 1) LoadingState else LoadingNextPageState)
        viewModelScope.launch {
            fetchPopularPhotosUsecase(page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (page == 1) {
                            // First page
                            _uiState.postValue(ContentState)
                            _photosList.postValue(dataState.data)
                        } else {
                            // Any other page
                            _uiState.postValue(ContentNextPageState)
                            var currentList = arrayListOf<PhotoModel>()
                            _photosList.value?.let { currentList.addAll(it) }
                            currentList.addAll(dataState.data)
                            _photosList.postValue(currentList)
                        }
                    }

                    is DataState.Error -> {
                        if (page == 1) {
                            _uiState.postValue(ErrorState(dataState.message))
                        } else {
                            _uiState.postValue(ErrorNextPageState(dataState.message))
                        }
                    }
                }
            }
        }
    }

    private fun searchPhotos(query: String, page: Int) {
        _uiState.postValue(if (page == 1) LoadingState else LoadingNextPageState)
        viewModelScope.launch {
            searchPhotosUsecase(query, page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (page == 1) {
                            // First page
                            _uiState.postValue(ContentState)
                            _photosList.postValue(dataState.data)
                        } else {
                            // Any other page
                            _uiState.postValue(ContentNextPageState)
                            var currentList = arrayListOf<PhotoModel>()
                            _photosList.value?.let { currentList.addAll(it) }
                            currentList.addAll(dataState.data)
                            _photosList.postValue(currentList)
                        }
                    }

                    is DataState.Error -> {
                        if (page == 1) {
                            _uiState.postValue(ErrorState(dataState.message))
                        } else {
                            _uiState.postValue(ErrorNextPageState(dataState.message))
                        }
                    }
                }
            }
        }
    }
}

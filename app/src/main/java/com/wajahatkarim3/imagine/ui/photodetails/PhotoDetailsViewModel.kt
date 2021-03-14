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
package com.wajahatkarim3.imagine.ui.photodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajahatkarim3.imagine.model.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableLiveData<PhotoDetailsUiState>()
    var uiStateLiveData: LiveData<PhotoDetailsUiState> = _uiState

    private var _photoModel = MutableLiveData<PhotoModel>()
    var photoModelLiveData: LiveData<PhotoModel> = _photoModel

    fun initPhotoModel(photo: PhotoModel) {
        _photoModel.value = photo
    }
}

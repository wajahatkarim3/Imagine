package com.wajahatkarim3.imagine.ui.photodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajahatkarim3.imagine.model.PhotoModel
import javax.inject.Inject

class PhotoDetailsViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableLiveData<PhotoDetailsUiState>()
    var uiStateLiveData: LiveData<PhotoDetailsUiState> = _uiState

    private var _photoModel = MutableLiveData<PhotoModel>()
    var photoModelLiveData: LiveData<PhotoModel> = _photoModel

    fun initPhotoModel(photo: PhotoModel) {
        _photoModel.value = photo
    }
}
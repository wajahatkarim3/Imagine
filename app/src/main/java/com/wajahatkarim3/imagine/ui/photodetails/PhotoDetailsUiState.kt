package com.wajahatkarim3.imagine.ui.photodetails

sealed class PhotoDetailsUiState

object LoadingState: PhotoDetailsUiState()
object ContentState: PhotoDetailsUiState()
class ErrorState(val message: String) : PhotoDetailsUiState()
package com.wajahatkarim3.imagine.ui.home

sealed class HomeUiState

object LoadingState: HomeUiState()
object LoadingNextPageState: HomeUiState()
object ContentState: HomeUiState()
object ContentNextPageState: HomeUiState()
object EmptyState: HomeUiState()
class ErrorState(val message: String) : HomeUiState()
class ErrorNextPageState(val message: String) : HomeUiState()
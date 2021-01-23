package com.wajahatkarim3.imagine.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wajahatkarim3.imagine.MainCoroutinesRule
import com.wajahatkarim3.imagine.data.DataState
import com.wajahatkarim3.imagine.data.usecases.FetchPopularPhotosUsecase
import com.wajahatkarim3.imagine.data.usecases.SearchPhotosUsecase
import com.wajahatkarim3.imagine.model.PhotoModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    // Subject under test
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var searchPhotosUsecase: SearchPhotosUsecase

    @MockK
    lateinit var fetchPopularPhotosUsecase: FetchPopularPhotosUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test when HomeViewModel is initialized, popular photos are fetched`() = runBlocking {
        // Given
        val givenPhotos = MockTestUtil.createPhotos(3)
        val uiObserver = mockk<Observer<HomeUiState>>(relaxed = true)
        val photosListObserver = mockk<Observer<List<PhotoModel>>>(relaxed = true)

        // When
        coEvery { fetchPopularPhotosUsecase.invoke(any(), any(), any()) }
            .returns(flowOf(DataState.success(givenPhotos)))

        // Invoke
        viewModel = HomeViewModel(fetchPopularPhotosUsecase, searchPhotosUsecase)
        viewModel.uiStateLiveData.observeForever(uiObserver)
        viewModel.photosListLiveData.observeForever(photosListObserver)

        // Then
        coVerify(exactly = 1) { fetchPopularPhotosUsecase.invoke() }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { photosListObserver.onChanged(match { it.size == givenPhotos.size }) }
    }
}
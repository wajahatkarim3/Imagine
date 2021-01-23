package com.wajahatkarim3.imagine.data.remote

import com.wajahatkarim3.imagine.MainCoroutinesRule
import com.wajahatkarim3.imagine.data.remote.api.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UnsplashApiServiceTest: ApiAbstract<UnsplashApiService>() {

    private lateinit var apiService: UnsplashApiService

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(UnsplashApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test loadPhotos() returns list of Photos`() = runBlocking {
        // Given
        enqueueResponse("/photos_list_response.json")

        // Invoke
        val response = apiService.loadPhotos(1, 10, "")
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody[0].id, `is`("LBI7cgq3pbM"))
        assertThat(responseBody[0].color, `is`("#60544D"))
        assertThat(responseBody[0].urls.thumb, `is`("https://images.unsplash.com/face-springmorning.jpg?q=75&fm=jpg&w=200&fit=max"))
        assertThat(responseBody[0].user.id, `is`("pXhwzz1JtQU"))
        assertThat(responseBody[0].user.username, `is`("poorkane"))
    }

    @Throws(IOException::class)
    @Test
    fun `test searchPhotos() returns list of Photos`() = runBlocking {
        // Given
        enqueueResponse("/search_photos_response.json")

        // Invoke
        val response = apiService.searchPhotos("", 1, 10)
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody.total, `is`(133))
        assertThat(responseBody.totalPages, `is`(7))
        assertThat(responseBody.photosList.size, `is`(1))

        assertThat(responseBody.photosList[0].id, `is`("eOLpJytrbsQ"))
        assertThat(responseBody.photosList[0].color, `is`("#A7A2A1"))
        assertThat(responseBody.photosList[0].urls.thumb, `is`("https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef"))
        assertThat(responseBody.photosList[0].user.id, `is`("Ul0QVz12Goo"))
        assertThat(responseBody.photosList[0].user.username, `is`("ugmonk"))
    }
}
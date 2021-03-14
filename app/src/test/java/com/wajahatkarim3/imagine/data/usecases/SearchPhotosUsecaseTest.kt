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
package com.wajahatkarim3.imagine.data.usecases

import com.MockTestUtil
import com.wajahatkarim3.imagine.data.DataState
import com.wajahatkarim3.imagine.data.repository.ImagineRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchPhotosUsecaseTest {

    @MockK
    private lateinit var repository: ImagineRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking SearchPhotosUsecase gives list of photos`() = runBlocking {
        // Given
        val usecase = SearchPhotosUsecase(repository)
        val givenPhotos = MockTestUtil.createPhotos(3)

        // When
        coEvery { repository.searchPhotos(any(), any(), any()) }
            .returns(flowOf(DataState.success(givenPhotos)))

        // Invoke
        val photosListFlow = usecase("", 1, 1)

        // Then
        MatcherAssert.assertThat(photosListFlow, CoreMatchers.notNullValue())

        val photosListDataState = photosListFlow.first()
        MatcherAssert.assertThat(photosListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(photosListDataState, CoreMatchers.instanceOf(DataState.Success::class.java))

        val photosList = (photosListDataState as DataState.Success).data
        MatcherAssert.assertThat(photosList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(photosList.size, CoreMatchers.`is`(givenPhotos.size))
    }
}

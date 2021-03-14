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
package com.wajahatkarim3.imagine.data.remote

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test message is not null or empty in Exception response`() {
        val exception = Exception("message")
        val apiResponse = ApiResponse.exception<String>(exception)
        MatcherAssert.assertThat(apiResponse.message, CoreMatchers.`is`("message"))
    }

    @Test
    fun `test data is is not null in Success response`() {
        val apiResponse = ApiResponse.create(200..299, Response.success("test_body"))
        if (apiResponse is ApiResponse.ApiSuccessResponse) {
            MatcherAssert.assertThat(apiResponse.data, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(apiResponse.data, CoreMatchers.`is`("test_body"))
        }
    }
}

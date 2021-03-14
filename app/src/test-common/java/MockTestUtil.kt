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
package com

import com.wajahatkarim3.imagine.data.remote.responses.SearchPhotosResponse
import com.wajahatkarim3.imagine.model.PhotoModel
import com.wajahatkarim3.imagine.model.PhotoUrlsModel
import com.wajahatkarim3.imagine.model.UserModel

class MockTestUtil {
    companion object {
        fun createPhotos(count: Int): List<PhotoModel> {
            return (0 until count).map {
                PhotoModel(
                    id = "$it",
                    created_at = "2016-05-03T11:00:28-04:00",
                    color = "#60544D",
                    description = "A man drinking a coffee.",
                    urls = createPhotoUrls(),
                    user = createUser(it)
                )
            }
        }

        fun createPhotoUrls(): PhotoUrlsModel {
            return PhotoUrlsModel(
                raw = "",
                full = "",
                regular = "",
                small = "",
                thumb = ""
            )
        }

        fun createUser(position: Int): UserModel {
            return UserModel(
                id = "$position",
                username = "username{$position}",
                name = "User Full Name $position"
            )
        }

        fun createSearchPhotosResponse(): SearchPhotosResponse {
            return SearchPhotosResponse(
                total = 3,
                totalPages = 1,
                photosList = createPhotos(3)
            )
        }
    }
}

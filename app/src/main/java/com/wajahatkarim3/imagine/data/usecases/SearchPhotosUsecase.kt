package com.wajahatkarim3.imagine.data.usecases

import com.wajahatkarim3.imagine.data.repository.ImagineRepository
import com.wajahatkarim3.imagine.utils.AppConstants
import javax.inject.Inject

/**
 * A use-case to search photos from Unsplash API.
 * @author Wajahat Karim
 */
class SearchPhotosUsecase @Inject constructor(private val repository: ImagineRepository) {
    suspend operator fun invoke(
        query: String,
        pageNum: Int = 1,
        pageSize: Int = AppConstants.API.PHOTOS_PER_PAGE
    ) = repository.searchPhotos(
        query = query,
        pageNumber = pageNum,
        pageSize = pageSize
    )
}
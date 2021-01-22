package com.wajahatkarim3.imagine.data.usecases

import com.wajahatkarim3.imagine.data.repository.ImagineRepository
import com.wajahatkarim3.imagine.utils.AppConstants
import javax.inject.Inject

/**
 * A use-case to load the popular photos from Unsplash API.
 * @author Wajahat Karim
 */
class FetchPopularPhotosUsecase @Inject constructor(private val repository: ImagineRepository) {
    suspend operator fun invoke(
        pageNum: Int = 1,
        pageSize: Int = AppConstants.API.PHOTOS_PER_PAGE,
        orderBy: String = "popular"
    ) = repository.loadPhotos(
        pageNumber = pageNum,
        pageSize = pageSize,
        orderBy = orderBy
    )
}
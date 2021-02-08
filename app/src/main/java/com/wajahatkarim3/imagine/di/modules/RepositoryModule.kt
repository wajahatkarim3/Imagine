package com.wajahatkarim3.imagine.di.modules

import android.app.Application
import com.wajahatkarim3.imagine.data.remote.UnsplashApiService
import com.wajahatkarim3.imagine.data.repository.ImagineRepository
import com.wajahatkarim3.imagine.data.repository.ImagineRepositoryImpl
import com.wajahatkarim3.imagine.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 * @author Wajahat Karim
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideImagineRepository(stringUtils: StringUtils, apiService: UnsplashApiService): ImagineRepository {
        return ImagineRepositoryImpl(stringUtils, apiService)
    }
}
package com.wajahatkarim3.imagine.di.modules

import androidx.lifecycle.ViewModelProvider
import com.wajahatkarim3.imagine.di.factories.AppViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * This modules is responsible for creating ViewModels for screens with the help of [AppViewModelProviderFactory].
 * This is required to easily inject dependencies in [ViewModel] as constructor parameters.
 * @author Wajahat Karim
 */
@Module
interface ViewModelsFactoryModule {

    @Binds
    fun bindAppViewModelFactory(factory: AppViewModelProviderFactory) : ViewModelProvider.Factory
}
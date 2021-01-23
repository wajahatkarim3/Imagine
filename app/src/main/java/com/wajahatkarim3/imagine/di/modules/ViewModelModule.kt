package com.wajahatkarim3.imagine.di.modules

import androidx.lifecycle.ViewModel
import com.wajahatkarim3.imagine.ui.home.HomeViewModel
import com.wajahatkarim3.imagine.ui.photodetails.PhotoDetailsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

/**
 * The Dagger Module for creating ViewModels based on the key-value Dagger bindings.
 * @author Wajahat Karim
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotoDetailsViewModel::class)
    abstract fun bindPhotoDetailsViewModel(viewModel: PhotoDetailsViewModel): ViewModel
}
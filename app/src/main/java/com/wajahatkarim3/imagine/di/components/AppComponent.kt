package com.wajahatkarim3.imagine.di.components

import android.app.Application
import com.wajahatkarim3.imagine.ImagineApp
import com.wajahatkarim3.imagine.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * The Dagger Component which provides the modules used throughout the app
 * @author Wajahat Karim
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelsFactoryModule::class,
        ViewModelModule::class,
        NetworkApiModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<ImagineApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: ImagineApp)
}
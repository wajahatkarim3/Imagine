package com.wajahatkarim3.imagine.di.modules

import com.wajahatkarim3.imagine.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The Dagger Module for Activities
 * @author Wajahat Karim
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
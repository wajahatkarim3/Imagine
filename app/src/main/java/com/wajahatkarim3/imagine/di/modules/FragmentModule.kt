package com.wajahatkarim3.imagine.di.modules

import com.wajahatkarim3.imagine.ui.home.HomeFragment
import com.wajahatkarim3.imagine.ui.photodetails.PhotoDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The Dagger Module for Fragments
 * @author Wajahat Karim
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindPhotoDetailsFragment(): PhotoDetailsFragment
}
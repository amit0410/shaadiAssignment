package com.example.shaadi.core.module

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {
    //TODO add all DB (Room), DAOs / Repos, Service (Retrofit) provides here
    @Singleton
    @Provides
    fun provideViewModelFactory(application: Application): ViewModelProvider.AndroidViewModelFactory {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

//    @Provides
//    fun provideEnvironment(application: Application): Environment {
//        val sharedPref = application.getSharedPreferences(
//            SharedPreferencesKeys.PREF_KEY,
//            SharedPreferencesKeys.PRIVATE_MODE
//        ).getString("chosenEnvironment", "")
//
//        return when (sharedPref) {
//            "tst" -> Environment.TST
//            "dev" -> Environment.DEV
//            "uat" -> Environment.UAT
//            "preprd" -> Environment.PREPRD
//            "prd" -> Environment.PRD
//            else -> Environment.DEFAULT
//        }
//    }
//
//    @Singleton
//    @Provides
//    fun providesResourceHelper(application: Application): ResourcesHelper {
//        return ResourcesHelper(application)
//    }
//
//    @Singleton
//    @Provides
//    fun provideSharedPreference(application: Application) =
//        application.getSharedPreferences(
//            SharedPreferencesKeys.PREF_KEY, SharedPreferencesKeys.PRIVATE_MODE)
//
//
//    @Provides
//    @Nullable
//    fun provideCurrentActivity() = AppInjector.currentActivity

}
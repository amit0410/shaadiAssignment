package com.example.shaadi.core.module

import android.app.Application
import com.example.shaadi.feature.db.DBHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDBHelper(application: Application) = DBHelper(application)
}
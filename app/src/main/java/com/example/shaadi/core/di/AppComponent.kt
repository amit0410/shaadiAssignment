package com.example.shaadi.core.di

import android.app.Application
import com.example.shaadi.ShaadiApplication
import com.example.shaadi.core.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ShaadiApplication)
}
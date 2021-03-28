package com.example.shaadi.core.module

import android.app.Application
import com.example.shaadi.feature.network.ShaadiApi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val shaadi = "shaadi"
private const val baseUrl = "https://randomuser.me"

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
private annotation class InternalNetworkApi(val value: String = "")

@Module
class NetworkModule {

    @Singleton
    @InternalNetworkApi
    @Provides
    fun provideOkHttpClient(
        application: Application
    ): OkHttpClient {
        return baseOkHttpClientBuilder(application).build()
    }

    private fun baseOkHttpClientBuilder(
        application: Application
    ) : OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            this.connectTimeout(30, TimeUnit.SECONDS)
            this.readTimeout(30, TimeUnit.SECONDS)
            this.writeTimeout(30, TimeUnit.SECONDS)
        }
    }

    private inline fun buildRetrofit(
        okHttpClient: Lazy<OkHttpClient>,
        block: Retrofit.Builder.() -> Unit
    ): Retrofit =
        Retrofit.Builder().apply {
            block()
            // common stuff
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call {
                    return okHttpClient.get().newCall(request)
                }
            })
        }.build()

    @Singleton
    @Provides
    @InternalNetworkApi(shaadi)
    fun provideShaadiRetrofit(
        @InternalNetworkApi client: Lazy<OkHttpClient>
    ): Retrofit =
        buildRetrofit(client) {
            addConverterFactory(MoshiConverterFactory.create())
            baseUrl(baseUrl)
        }

    @Singleton
    @Provides
    fun provideShaadiApi(@InternalNetworkApi(shaadi) retrofit: Retrofit): ShaadiApi =
        retrofit.create(ShaadiApi::class.java)
}
package com.example.shaadi.feature.network

import com.example.shaadi.feature.contract.ProfileResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface ShaadiApi {

    @Headers("Content-Type: application/json")
    @GET("/api/")
    fun getProfiles(
        @retrofit2.http.Query("results") res: Int?
    ): Single<ProfileResponse>
}
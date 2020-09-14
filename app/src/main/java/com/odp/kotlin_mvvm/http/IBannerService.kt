package com.odp.kotlin_mvvm.http

import com.odp.kotlin_mvvm.bean.BannerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface IBannerService {

    @GET("index")
    fun getBannerData(
        @Query("type") type:String,
        @Query("key") key:String
    ): Observable<BannerResponse>

}
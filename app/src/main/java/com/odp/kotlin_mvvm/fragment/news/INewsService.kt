package com.odp.kotlin_mvvm.fragment.news

import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.bean.BannerResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author  ChenHh
 * @time   2020/12/9 15:08
 * @des
 **/
interface INewsService {
    @GET("index")
    suspend fun getBannerData(
        @Query("type") type:String,
        @Query("key") key:String
    ): BannerResponse
}
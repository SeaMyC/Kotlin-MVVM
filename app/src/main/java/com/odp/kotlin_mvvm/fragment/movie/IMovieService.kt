package com.odp.kotlin_mvvm.fragment.movie

import com.odp.kotlin_mvvm.bean.BannerResponse
import com.odp.kotlin_mvvm.bean.DiscoverMovieEntity
import com.odp.kotlin_mvvm.bean.MovieEntity
import com.odp.kotlin_mvvm.bean.MovieResponse
import com.odp.kotlin_mvvm.coroutine.ResponseData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author  ChenHh
 * @time   2020/12/9 15:08
 * @des
 **/
interface IMovieService {

    //获取首页映列表
    @GET("mmdb/movie/v3/list/hot.json")
    suspend fun getHostList(@Query("limit") limit: Int)
            : ResponseData<MovieEntity>


    @GET("mmdb/movie/lp/list.json")
    suspend fun getDiscoverList()
            : ResponseData<List<DiscoverMovieEntity>>
}
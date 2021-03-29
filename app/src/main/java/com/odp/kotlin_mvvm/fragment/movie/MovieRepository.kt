package com.odp.kotlin_mvvm.fragment.movie

import com.odp.kotlin_mvvm.bean.BannerResponse
import com.odp.kotlin_mvvm.bean.MovieEntity
import com.odp.kotlin_mvvm.bean.MovieResponse
import com.odp.kotlin_mvvm.coroutine.BaseRepository
import com.odp.kotlin_mvvm.coroutine.ResponseData
import com.odp.kotlin_mvvm.coroutine.RetrofitClient
import com.odp.kotlin_mvvm.fragment.news.INewsService

/**
 * @author  ChenHh
 * @time   2020/12/9 15:07
 * @des
 **/

open class MovieRepository : BaseRepository() {

    suspend fun getHotList(limit: Int): ResponseData<MovieEntity> = request {
        RetrofitClient.createMovie(IMovieService::class.java)
            .getHostList(limit)
    }

}
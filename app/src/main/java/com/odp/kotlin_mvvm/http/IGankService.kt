package com.odp.kotlin_mvvm.http

import com.odp.kotlin_mvvm.bean.BaseGankIOResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author  ChenHh
 * @time   2020/8/3 15:35
 * @des  gankIOService
 **/
interface IGankService {

    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    fun getGankIoData(
        @Path("category") category: String?,
        @Path("type") type: String,
        @Path("page") page: Int,
        @Path("count") count: Int
    ): Observable<BaseGankIOResponse>

}
package com.odp.kotlin_mvvm.fragment.news

import com.odp.kotlin_mvvm.bean.BannerEntity

/**
 * @author  ChenHh
 * @time   2020/12/9 15:07
 * @des
 **/

open class NewsRepository : BaseRepository() {

    suspend fun getNewsData(type:String): ResponseData<List<BannerEntity>> = request {
        RetrofitClient.create(INewsService::class.java).getBannerData(type,"f527601230ad4a74c08491c13db2b78f");
    }
}
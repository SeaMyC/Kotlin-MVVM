package com.odp.kotlin_mvvm.http

import com.odp.kotlin_mvvm.config.BANNER_API_URL
import com.odp.kotlin_mvvm.config.GANK_API_URL

/**
 * @author  ChenHh
 * @time   2020/8/3 15:34
 * @des  httpServiceClient
 **/
 enum class HttpServiceClient {
    INSTANCE;

    open fun getGankIOService(): IGankService {
        return RetrofitClient.INSTANCE.getBuilder(GANK_API_URL).build().create(IGankService::class.java)
    }
    open fun getBannerService(): IBannerService {
        return RetrofitClient.INSTANCE.getBuilder(BANNER_API_URL).build().create(IBannerService::class.java)
    }

}
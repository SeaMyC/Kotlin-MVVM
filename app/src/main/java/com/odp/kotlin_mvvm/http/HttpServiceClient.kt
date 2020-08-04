package com.odp.kotlin_mvvm.http

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

}
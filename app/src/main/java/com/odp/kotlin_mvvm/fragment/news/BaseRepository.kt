package com.odp.kotlin_mvvm.fragment.news

import com.odp.kotlin_mvvm.bean.BannerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author  ChenHh
 * @time   2020/12/9 15:04
 * @des
 **/
open class BaseRepository {
    suspend fun <T : Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        return withContext(Dispatchers.IO) { call.invoke() }.apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理
            when (errorCode) {
                100 -> throw TokenInvalidException()
            }
            call()
        }
    }

    suspend fun <T : Any> requestBanner(call: suspend () -> BannerResponse): BannerResponse {
        return withContext(Dispatchers.IO) { call.invoke() }.apply {
            call()
        }
    }

    class TokenInvalidException(msg: String = "token invalid") : Exception(msg)
}
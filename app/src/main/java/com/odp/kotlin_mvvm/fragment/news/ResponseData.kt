package com.odp.kotlin_mvvm.fragment.news


data class ResponseData<out T>(val errorCode: Int, val errorMsg: String, val data: T)
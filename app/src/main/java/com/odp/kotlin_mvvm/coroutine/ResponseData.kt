package com.odp.kotlin_mvvm.coroutine


data class ResponseData<out T>(val errorCode: Int, val errorMsg: String, val data: T)
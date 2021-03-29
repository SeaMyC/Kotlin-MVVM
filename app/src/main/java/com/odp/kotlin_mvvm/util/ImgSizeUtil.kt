package com.odp.kotlin_mvvm.util

import androidx.annotation.NonNull

/**
 * @author  ChenHh
 * @time   2021/3/29 17:13
 * @des
 **/
public class ImgSizeUtil {
    //部分图片通过拼接url
    fun resetPicUrl(@NonNull url: String, size: String): String? {
        if (url.contains("@")) {
            val origin = url.substring(0, url.indexOf("@"))
            return origin.replace("/w.h/", "/") + size
        }
        return if (!url.contains("/w.h/")) {
            url + size
        } else url.replace("/w.h/", "/") + size
    }

    //通过替换w.h获取图片
    fun processUrl(@NonNull url: String, width: Int, height: Int): String? {
        return url.replace("/w.h/", "/$width.$height/")
    }
}
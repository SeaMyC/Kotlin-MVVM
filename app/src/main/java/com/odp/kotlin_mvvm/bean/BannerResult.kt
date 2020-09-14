package com.odp.kotlin_mvvm.bean

/**
 * @author  ChenHh
 * @time   2020/9/14 15:17
 * @des  result
 **/
data class BannerResult(
    var stat: String,
    var data:  List<BannerEntity> = listOf()
)
package com.odp.kotlin_mvvm.bean

/**
 * @author  ChenHh
 * @time   2021/3/25 11:59
 * @des
 **/
data class MovieEntity(
    var coming: List<HotMovieEntity>,
    var hot: List<HotMovieEntity>,
    var total: String
)
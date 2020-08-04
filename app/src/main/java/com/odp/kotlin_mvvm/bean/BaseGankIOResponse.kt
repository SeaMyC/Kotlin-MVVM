package com.odp.kotlin_mvvm.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author  ChenHh
 * @time   2020/8/3 16:02
 * @des
 **/
data class BaseGankIOResponse(
    var data: List<GankIoEntity> = listOf(),
    var page: Int = 0,
    var status: Int = 0,

    @SerializedName("total_counts")
    var totalCounts: Int = 0,

    @SerializedName("page_count")
    var pageCount: Int = 0

)

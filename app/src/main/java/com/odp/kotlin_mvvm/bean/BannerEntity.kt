package com.odp.kotlin_mvvm.bean

import com.google.gson.annotations.SerializedName

/**
 * @author  ChenHh
 * @time   2020/9/2 15:14
 * @des
 **/

data class BannerEntity(
    /**
    "uniquekey":"6830e0a16a682d43bd722b5a73fbbac7",
    "title":"2020中式台球大师赛启动 乔冰：总奖金提高200万",
    "date":"2020-09-02 10:19",
    "category":"体育",
    "author_name":"网易体育",
    "url":"https:\/\/mini.eastday.com\/mobile\/200902101955179.html",
    "thumbnail_pic_s":"https:\/\/06imgmini.eastday.com\/mobile\/20200902\/20200902101955_0d1a3d96e769507ac9cd5cfd50dd87c0_3_mwpm_03200403.jpg",
    "thumbnail_pic_s02":"http:\/\/06imgmini.eastday.com\/mobile\/20200902\/20200902101955_0d1a3d96e769507ac9cd5cfd50dd87c0_1_mwpm_03200403.jpg",
    "thumbnail_pic_s03":"http:\/\/06imgmini.eastday.com\/mobile\/20200902\/20200902101955_0d1a3d96e769507ac9cd5cfd50dd87c0_2_mwpm_03200403.jpg"
     */
    val uniquekey: String? = null,
    val date: String? = null,
    val category: String? = null,
    val url: String? = null,
    val title: String? = null,

    @SerializedName("author_name")
    val authorName: String? = null,

    @SerializedName("thumbnail_pic_s")
    val pic1: String? = null,
    @SerializedName("thumbnail_pic_s02")
    val pic2: String? = null,
    @SerializedName("thumbnail_pic_s03")
    val pic3: String? = null
)
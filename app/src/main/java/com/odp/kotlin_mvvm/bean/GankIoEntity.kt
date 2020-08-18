package com.odp.kotlin_mvvm.bean

import com.google.gson.annotations.SerializedName

/**
 * @author  ChenHh
 * @time   2020/8/3 16:18
 * @des
 **/
data class GankIoEntity(
    /**
     * _id : 5e959250808d6d2fe6b56eda
     * author : 鸢媛
     * category : Girl
     * createdAt : 2020-05-25 08:00:00
     * desc : 与其安慰自己平凡可贵，
     * 不如拼尽全力活得漂亮。 ​ ​​​​
     * images : ["http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2"]
     * likeCounts : 3
     * publishedAt : 2020-05-25 08:00:00
     * stars : 1
     * title : 第96期
     * type : Girl
     * url : http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2
     * views : 5604
     */
    @SerializedName("_id")
    val id: String? = null,
    val author: String? = null,
    var category: String? = null,
    val createdAt: String? = null,
    val desc: String? = null,
    val likeCounts: Int = 0,
    val publishedAt: String? = null,
    val stars: Int = 0,
    val title: String? = null,
    val type: String? = null,
    val url: String? = null,
    val views: Int = 0,
    val images: List<String>? = null


)
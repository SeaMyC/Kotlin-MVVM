package com.odp.kotlin_mvvm.fragment.main

import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.base.BaseViewModel
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.http.HttpServiceClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author  ChenHh
 * @time   2020/9/14 15:22
 * @des  bannerModel
 **/
class NewsModel : BaseViewModel() {

    var newsList: MutableLiveData<List<BannerEntity>> = MutableLiveData()

    fun getNewsList(type:String) {
        val subscribe = HttpServiceClient.INSTANCE.getBannerService()
            .getBannerData(type,"f527601230ad4a74c08491c13db2b78f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bean -> newsList.postValue(bean.result.data) }

    }

}
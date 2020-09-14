package com.odp.kotlin_mvvm.fragment.main

import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.base.BaseViewModel
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.http.HttpServiceClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author  ChenHh
 * @time   2020/9/14 15:22
 * @des  bannerModel
 **/
class BannerModel : BaseViewModel() {

    var bannerList: MutableLiveData<List<BannerEntity>> = MutableLiveData()

    fun getBannerList() {
        val subscribe = HttpServiceClient.INSTANCE.getBannerService()
            .getBannerData("top","f527601230ad4a74c08491c13db2b78f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bean -> bannerList.postValue(bean.result.data) }

    }
}
package com.odp.kotlin_mvvm.fragment.girl

import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.base.BaseViewModel
import com.odp.kotlin_mvvm.bean.GankIoEntity
import com.odp.kotlin_mvvm.http.HttpServiceClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author  ChenHh
 * @time   2020/8/3 15:14
 * @des  girlViewModel
 **/
class GirlViewModel : BaseViewModel() {
    private var page: Int = 1
    var girlList: MutableLiveData<List<GankIoEntity>> = MutableLiveData()
    var isRefresh = false

    fun getGirlList(refresh: Boolean) {
        isRefresh = refresh
        if (refresh) {
            page = 1
        } else {
            page++
        }
        val subscribe = HttpServiceClient.INSTANCE.getGankIOService()
            .getGankIoData("Girl", "Girl", page, PAGE_SIZE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { bean -> girlList.postValue(bean.data) }

    }
}
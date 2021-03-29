package com.odp.kotlin_mvvm.fragment.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_SPORT
import com.odp.kotlin_mvvm.coroutine.XBaseViewModel

/**
 * @author  ChenHh
 * @time   2020/12/9 14:52
 * @des
 **/

class NewsViewModel @ViewModelInject constructor() : XBaseViewModel() {
    val data: MutableLiveData<List<BannerEntity>> by lazy {
        MutableLiveData<List<BannerEntity>>().also {
            loadData(NEWS_TYPE_SPORT)
        }
    }

    private val repository = NewsRepository()

    fun loadData(type: String) = launchUI {
        val newsData = repository.getNewsData(type)
        data.postValue(newsData.result.data)
    }
}
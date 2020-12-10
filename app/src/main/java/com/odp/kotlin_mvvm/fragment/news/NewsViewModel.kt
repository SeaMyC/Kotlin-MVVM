package com.odp.kotlin_mvvm.fragment.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_SPORT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author  ChenHh
 * @time   2020/12/9 14:52
 * @des
 **/

class NewsViewModel @ViewModelInject constructor() : XBaseViewModel() {
    val data: MutableLiveData<List<BannerEntity>> by lazy { MutableLiveData<List<BannerEntity>>() }
    private val repository = NewsRepository()
    fun getData(): LiveData<List<BannerEntity>> {
        return data;
    }

    fun loadData(type: String) = launchUI {
        data.value = repository.getNewsData(type).data
    }
    fun loadData2(type: String) = viewModelScope.launch {
        data.value = repository.getNewsData(type).data
    }
}
package com.odp.kotlin_mvvm.fragment.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.bean.DiscoverMovieEntity
import com.odp.kotlin_mvvm.bean.HotMovieEntity
import com.odp.kotlin_mvvm.config.DEFAULT_LIMIT_VALUE
import com.odp.kotlin_mvvm.coroutine.XBaseViewModel

/**
 * @author  ChenHh
 * @time   2020/12/9 14:52
 * @des
 **/

class MovieViewModel @ViewModelInject constructor() : XBaseViewModel() {

    val hotMovieData: MutableLiveData<List<HotMovieEntity>> by lazy {
        MutableLiveData<List<HotMovieEntity>>().also {
            requestHotList(DEFAULT_LIMIT_VALUE)
        }
    }

    val discoverData: MutableLiveData<List<DiscoverMovieEntity>> by lazy {
        MutableLiveData<List<DiscoverMovieEntity>>().also {
            requestDiscoverList()
        }
    }

    private val repository = MovieRepository()

    fun requestHotList(limit: Int) = launchUI {
        val hotList = repository.getHotList(limit)
        hotMovieData.postValue(hotList.data.hot)
    }

    fun requestDiscoverList() = launchUI {
        val discoverList = repository.getDiscoverList()
        discoverData.postValue(discoverList.data)
    }
}
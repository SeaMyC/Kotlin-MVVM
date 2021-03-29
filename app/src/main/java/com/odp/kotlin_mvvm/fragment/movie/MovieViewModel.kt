package com.odp.kotlin_mvvm.fragment.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.odp.kotlin_mvvm.bean.HotMovieEntity
import com.odp.kotlin_mvvm.config.DEFAULT_LIMIT_VALUE
import com.odp.kotlin_mvvm.coroutine.XBaseViewModel

/**
 * @author  ChenHh
 * @time   2020/12/9 14:52
 * @des
 **/

class MovieViewModel @ViewModelInject constructor() : XBaseViewModel() {

    val data: MutableLiveData<List<HotMovieEntity>> by lazy {
        MutableLiveData<List<HotMovieEntity>>().also {
            requestHotList(DEFAULT_LIMIT_VALUE)
        }
    }

    private val repository = MovieRepository()

    fun requestHotList(limit: Int) = launchUI {
        val hotList = repository.getHotList(limit)
        data.postValue(hotList.data.hot)
    }
}
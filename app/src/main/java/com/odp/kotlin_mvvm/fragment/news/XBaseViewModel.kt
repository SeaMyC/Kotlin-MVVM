package com.odp.kotlin_mvvm.fragment.news

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @author  ChenHh
 * @time   2020/12/9 14:30
 * @des
 **/
open class XBaseViewModel : ViewModel(), LifecycleObserver {
    private val error by lazy { MutableLiveData<Exception>() }
    private val finally by lazy { MutableLiveData<Int>() }

    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            withTimeout(5000) {
                block()
            }
        } catch (e: Exception) {
            error.value = e
        } finally {
            finally.value = 200
        }
    }


}
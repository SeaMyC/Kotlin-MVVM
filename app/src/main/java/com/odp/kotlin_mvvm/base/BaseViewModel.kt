package com.odp.kotlin_mvvm.base

import androidx.lifecycle.ViewModel
import com.odp.kotlin_mvvm.http.HttpServiceClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author  ChenHh
 * @time   2020/8/3 15:15
 * @des  baseViewModel
 **/
open class BaseViewModel : ViewModel() {
   protected val PAGE_SIZE: Int = 20
}
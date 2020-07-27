package com.odp.kotlin_mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author  ChenHh
 * @time   2020/7/23 10:47
 * @des
 **/
abstract class BinDingActivity<BA : ViewDataBinding>: AppCompatActivity() {
    protected lateinit var bindingView: BA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         bindingView = DataBindingUtil.setContentView(this, getLayout())
    }

    protected abstract fun getLayout(): Int
}
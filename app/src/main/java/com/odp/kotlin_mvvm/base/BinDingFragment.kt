package com.odp.kotlin_mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.util.zip.Inflater

/**
 * @author  ChenHh
 * @time   2020/7/27 11:00
 * @des
 **/
abstract class BinDingFragment<BF : ViewDataBinding> : Fragment() {
    protected lateinit var bindingView: BF

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return bindingView.root
    }

    protected abstract fun getLayout(): Int
}
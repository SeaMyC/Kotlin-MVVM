package com.odp.kotlin_mvvm.fragment

import android.os.Bundle
import android.view.View
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.databinding.FragmentGirlBinding

/**
 * @author  ChenHh
 * @time   2020/7/23 16:48
 * @des
 **/
class GirlFragment : BinDingFragment<FragmentGirlBinding>() {
    private var indexNumber: Int = 0

    fun setIndex(index: Int) {
        indexNumber = index
    }

    override fun getLayout(): Int {
        return R.layout.fragment_girl
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView.tvText.text = indexNumber.toString()
    }
}
package com.odp.kotlin_mvvm.fragment.girl

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.bean.GankIoEntity
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
        val girlAdapter = GirlAdapter()
        val linearLayoutManager = LinearLayoutManager(context)
        bindingView.recyclerView.adapter = girlAdapter;
        bindingView.recyclerView.layoutManager = linearLayoutManager

        val model = ViewModelProvider(this).get(GirlViewModel::class.java)
        model.getGirlList(true)
        model.girlList.observe(viewLifecycleOwner, Observer<List<GankIoEntity>> { bean ->
            if (bean != null) {
                   girlAdapter.setDatas(bean)
            }
        })
    }
}
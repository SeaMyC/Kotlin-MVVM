package com.odp.kotlin_mvvm.fragment.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_SPORT
import com.odp.kotlin_mvvm.databinding.FragmentNewsBinding
import com.odp.kotlin_mvvm.fragment.main.SportAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author  ChenHh
 * @time   2020/12/9 14:20
 * @des
 **/
@AndroidEntryPoint
class NewsFragment : BinDingFragment<FragmentNewsBinding>() {
    private val model: NewsViewModel by viewModels()

    override fun getLayout(): Int {
        return R.layout.fragment_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = SportAdapter()
        bindingView.recyclerView.adapter = newsAdapter
        bindingView.recyclerView.layoutManager = LinearLayoutManager(context)

        model.data.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                val mutableList = result as MutableList<BannerEntity>
                newsAdapter.setDataList(mutableList)
                bindingView.refresh.isRefreshing = false
            }
        })

        bindingView.refresh.setOnRefreshListener {
            bindingView.refresh.isRefreshing = true
            model.loadData(NEWS_TYPE_SPORT)
        }
    }
}
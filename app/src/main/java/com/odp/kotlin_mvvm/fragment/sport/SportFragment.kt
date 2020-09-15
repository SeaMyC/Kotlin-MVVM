package com.odp.kotlin_mvvm.fragment.sport

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.base.GirlActivity
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_SPORT
import com.odp.kotlin_mvvm.databinding.FragmentSportBinding
import com.odp.kotlin_mvvm.fragment.girl.GirlAdapter
import com.odp.kotlin_mvvm.fragment.main.NewsAdapter
import com.odp.kotlin_mvvm.fragment.main.NewsModel
import java.util.*

/**
 * @author  ChenHh
 * @time   2020/9/15 15:36
 * @des  sport fragment
 **/
class SportFragment : BinDingFragment<FragmentSportBinding>() {

    override fun getLayout(): Int {
        return R.layout.fragment_sport
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = ViewModelProvider(this).get(NewsModel::class.java)
        val newsAdapter = NewsAdapter()
        bindingView.recyclerView.adapter = newsAdapter
        bindingView.recyclerView.layoutManager = LinearLayoutManager(context)
        model.getNewsList(NEWS_TYPE_SPORT)
        model.newsList.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                val mutableList = result as MutableList<BannerEntity>
                newsAdapter.setDataList(mutableList)
                bindingView.refresh.isRefreshing = false
            }
        })

        bindingView.refresh.setOnRefreshListener {
            bindingView.refresh.isRefreshing = true
            model.getNewsList(NEWS_TYPE_SPORT)
        }

        newsAdapter.setItemClickListener(object : NewsAdapter.IWealItemListener {
            override fun onItemListener(str: String?, view: View?) {
//                val intent = Intent(activity, GirlActivity::class.java)
//                intent.putExtra("girl_image_url",str)
//                startActivity(
//                    intent,
//                    ActivityOptions.makeSceneTransitionAnimation(activity, view, "girl_image")
//                        .toBundle()
//                )
            }
        })

    }
}
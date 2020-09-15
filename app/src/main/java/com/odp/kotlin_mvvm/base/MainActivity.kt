package com.odp.kotlin_mvvm.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.adapter.TabLayoutAdapter
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_TOP
import com.odp.kotlin_mvvm.databinding.ActivityMainBinding
import com.odp.kotlin_mvvm.fragment.main.NewsModel

class MainActivity : BinDingActivity<ActivityMainBinding>() {
    private lateinit var bannerModel: NewsModel
    private val titles = listOf("sport", "girl", "C")
    private var isFirst: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabLayoutAdapter = TabLayoutAdapter(this)
        bindingView.viewPager.adapter = tabLayoutAdapter
        //viewPager2的写法
        TabLayoutMediator(
            bindingView.tab,
            bindingView.viewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()

        val banner = bindingView.banner;

        bannerModel = ViewModelProvider(this).get(NewsModel::class.java)
        bannerModel.newsList.observe(this, Observer<List<BannerEntity>> { bannerList ->
            if (bannerList.isNotEmpty()) {
                val subList = bannerList.subList(0, 5);
                banner.setData(subList)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            bannerModel.getNewsList(NEWS_TYPE_TOP)
            isFirst = false
        }

    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}

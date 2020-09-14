package com.odp.kotlin_mvvm.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.adapter.TabLayoutAdapter
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.bean.GankIoEntity
import com.odp.kotlin_mvvm.databinding.ActivityMainBinding
import com.odp.kotlin_mvvm.fragment.main.BannerModel

class MainActivity : BinDingActivity<ActivityMainBinding>() {
    private lateinit var bannerModel: BannerModel
    private val titles = listOf("A", "B", "C")

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

        bannerModel = ViewModelProvider(this).get(BannerModel::class.java)
        bannerModel.bannerList.observe(this, Observer<List<BannerEntity>> { bannerList ->
            if (bannerList.isNotEmpty()) {
                banner.setData(bannerList)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        bannerModel.getBannerList()

    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}

package com.odp.kotlin_mvvm

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.odp.kotlin_mvvm.adapter.TabLayoutAdapter
import com.odp.kotlin_mvvm.base.BinDingActivity
import com.odp.kotlin_mvvm.databinding.ActivityMainBinding

class MainActivity : BinDingActivity<ActivityMainBinding>() {
    private val titles = listOf("A", "B", "C")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabLayoutAdapter = TabLayoutAdapter(this)
        bindingView.viewPager.adapter = tabLayoutAdapter
        //viewPager2的写法
        TabLayoutMediator(
            bindingView.tab,
            bindingView.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles[position]
            }).attach()

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

}

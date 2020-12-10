package com.odp.kotlin_mvvm.base

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.adapter.TabLayoutAdapter
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.config.NEWS_TYPE_TOP
import com.odp.kotlin_mvvm.databinding.ActivityMainBinding
import com.odp.kotlin_mvvm.fragment.main.SportModel
import com.odp.kotlin_mvvm.util.BannerView
import com.odp.kotlin_mvvm.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BinDingActivity<ActivityMainBinding>() {
    private lateinit var bannerModel: SportModel
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

        val banner = bindingView.banner
        banner.setBannerClickListener(object : BannerView.IBannerClickListener {
            override fun onItemClick(url: String?) {
                val intent = Intent(this@MainActivity, WebActivity::class.java)
                intent.putExtra("web_url", url)
                startActivity(intent)
            }
        })

        bannerModel = ViewModelProvider(this).get(SportModel::class.java)
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

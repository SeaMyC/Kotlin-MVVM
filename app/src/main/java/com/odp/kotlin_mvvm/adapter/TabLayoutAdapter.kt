package com.odp.kotlin_mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.odp.kotlin_mvvm.fragment.girl.GirlFragment
import com.odp.kotlin_mvvm.fragment.movie.MovieFragment
import com.odp.kotlin_mvvm.fragment.news.NewsFragment

/**
 * @author  ChenHh
 * @time   2020/7/23 16:24
 * @des
 **/
class TabLayoutAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var fragments : List<Fragment>

    init {
        val movieFragment = MovieFragment()
        val girlFragment = GirlFragment()
        val newsFragment = NewsFragment()
        fragments = listOf(movieFragment, girlFragment, newsFragment)

    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
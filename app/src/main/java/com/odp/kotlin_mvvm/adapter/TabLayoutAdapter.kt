package com.odp.kotlin_mvvm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.odp.kotlin_mvvm.fragment.girl.GirlFragment

/**
 * @author  ChenHh
 * @time   2020/7/23 16:24
 * @des
 **/
class TabLayoutAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var fragments : List<Fragment>

    init {
        val girlFragment1 = GirlFragment()
        girlFragment1.setIndex(0)
        val girlFragment2 = GirlFragment()
        girlFragment2.setIndex(1)
        val girlFragment3 = GirlFragment()
        girlFragment3.setIndex(2)
        fragments = listOf(girlFragment1, girlFragment2, girlFragment3)

    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
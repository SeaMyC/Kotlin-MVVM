package com.odp.kotlin_mvvm.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.bean.DiscoverMovieEntity
import com.odp.kotlin_mvvm.bean.HotMovieEntity
import com.odp.kotlin_mvvm.databinding.FragmentMovieBinding
import com.odp.kotlin_mvvm.util.DiscoverItemDecoration
import com.odp.kotlin_mvvm.util.HotMovieItemDecoration
import com.odp.kotlin_mvvm.util.ScreenUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author  ChenHh
 * @time   2021/3/25 11:10
 * @des
 **/

@AndroidEntryPoint
class MovieFragment : BinDingFragment<FragmentMovieBinding>() {
    private lateinit var hotMovieAdapter: HotMovieAdapter
    private lateinit var discoverAdapter: DiscoverMovieAdapter
    private val model: MovieViewModel by viewModels()

    override fun getLayout(): Int {
        return R.layout.fragment_movie
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.hotMovieData.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                bindingView.refresh.isRefreshing = false
                val mutableList = result as MutableList<HotMovieEntity>
                hotMovieAdapter.setDataList(mutableList)
                bindingView.hot.tvTotalCount.text= String.format(getString(R.string.movie_hot_total_count),mutableList.size)
            }
        })
        hotMovieAdapter = HotMovieAdapter()
        bindingView.hot.recyclerView.adapter = hotMovieAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bindingView.hot.recyclerView.layoutManager = linearLayoutManager
        bindingView.hot.recyclerView.addItemDecoration(HotMovieItemDecoration(ScreenUtils().dpToPxInt(context,5F)))

        bindingView.hot.tvTitle.text = getString(R.string.movie_hot)
        bindingView.refresh.setOnRefreshListener {
            bindingView.refresh.isRefreshing = true
            model.requestHotList(50)
            model.requestDiscoverList()
        }

        model.discoverData.observe(viewLifecycleOwner, Observer { result ->
            if (result.isNotEmpty()) {
                bindingView.refresh.isRefreshing = false
                val mutableList = result as MutableList<DiscoverMovieEntity>
                discoverAdapter.setDataList(mutableList)
            }
        })
        discoverAdapter = DiscoverMovieAdapter()
        bindingView.rvDiscover.adapter = discoverAdapter
        val discoverLinearLayoutManager = LinearLayoutManager(context)
        discoverLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        bindingView.rvDiscover.layoutManager = discoverLinearLayoutManager
        bindingView.rvDiscover.addItemDecoration(DiscoverItemDecoration(ScreenUtils().dpToPxInt(context,10F)))


    }
}

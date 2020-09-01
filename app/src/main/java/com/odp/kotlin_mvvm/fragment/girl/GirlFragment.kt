package com.odp.kotlin_mvvm.fragment.girl

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingFragment
import com.odp.kotlin_mvvm.base.GirlActivity
import com.odp.kotlin_mvvm.bean.GankIoEntity
import com.odp.kotlin_mvvm.databinding.FragmentGirlBinding
import com.odp.kotlin_mvvm.util.RecyclerItemDecoration

/**
 * @author  ChenHh
 * @time   2020/7/23 16:48
 * @des
 **/
class GirlFragment : BinDingFragment<FragmentGirlBinding>() {
    private var lastVisibleItem: Int = 0
    private var indexNumber: Int = 0

    fun setIndex(index: Int) {
        indexNumber = index
    }

    override fun getLayout(): Int {
        return R.layout.fragment_girl
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val girdLayoutManager = GridLayoutManager(context, 2)
        val girlAdapter = GirlAdapter(girdLayoutManager)
        bindingView.recyclerView.adapter = girlAdapter;
        bindingView.recyclerView.layoutManager = girdLayoutManager
        bindingView.recyclerView.addItemDecoration(RecyclerItemDecoration(10, 2))


        val model = ViewModelProvider(this).get(GirlViewModel::class.java)
        model.getGirlList(true)
        model.girlList.observe(viewLifecycleOwner, Observer<List<GankIoEntity>> { bean ->
            if (model.isRefresh) {
                if (bean != null) {
                    girlAdapter.setDataList(bean as MutableList<GankIoEntity>?)
                    bindingView.refresh.isRefreshing = false
                }
            } else {
                val mutableList = bean as MutableList<GankIoEntity>
                girlAdapter.loadMoreData(mutableList, bean.isNotEmpty())
            }
        })


        initEvent(girlAdapter, model, girdLayoutManager)

    }

    private fun initEvent(
        girlAdapter: GirlAdapter,
        model: GirlViewModel,
        girdLayoutManager: GridLayoutManager
    ) {
        bindingView.refresh.setOnRefreshListener {
            bindingView.refresh.isRefreshing = true
            girlAdapter.resetDatas()
            model.getGirlList(true)
        }

        bindingView.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // 在newState为滑到底部时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (!girlAdapter.isFadeTips() && lastVisibleItem + 1 == girlAdapter.itemCount) {
                        model.getGirlList(false)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 在滑动完成后，拿到最后一个可见的item的位置
                lastVisibleItem = girdLayoutManager.findLastVisibleItemPosition()
            }
        })

        girlAdapter.setItemClickListener(object : GirlAdapter.IWealItemListener {
            override fun onItemListener(str: String?, view: View?) {
                val intent = Intent(activity, GirlActivity::class.java)
                intent.putExtra("girl_image_url",str)
                startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(activity, view, "girl_image")
                        .toBundle()
                )
            }
        })
    }
}
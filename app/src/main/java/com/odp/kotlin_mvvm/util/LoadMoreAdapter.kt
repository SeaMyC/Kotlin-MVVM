package com.odp.kotlin_mvvm.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.databinding.LayoutFootviewBinding
import com.odp.kotlin_mvvm.fragment.girl.GirlAdapter

/**
 * @author  ChenHh
 * @time   2020/8/18 9:40
 * @des  loadMoreAdapter
 **/
abstract class LoadMoreAdapter<VH> : RecyclerView.Adapter<ViewHolder>() {
    private var datas: MutableList<VH> = mutableListOf() // 数据源

    private val normalType = 0 // 第一种ViewType，正常的item

    private val footType = 1 // 第二种ViewType，底部的提示View

    private var hasMore = true // 变量，是否有更多数据

    private var fadeTips = false // 变量，是否隐藏了底部的提示

    private var context: Context? = null

    override fun getItemCount(): Int {
        return getSubItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) { //最后一个
            footType
        } else {
            normalType
        }
    }

    abstract fun getSubItemCount(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                context = parent.context
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        return if (viewType == normalType) {
            setNormalHolder(parent, viewType)
        } else {
            FootHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_footview, parent, false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        // 如果是正常的imte，直接设置TextView的值
        if (holder is FootHolder) {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            holder.tips.visibility = View.VISIBLE
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore) {
                // 不隐藏footView提示
                fadeTips = false
                if (datas.isNotEmpty()) {
                    holder.tips.text = "加载更多数据..."
                }
            } else {
                if (datas.isNotEmpty()) {
                    Toast.makeText(context, "无更多数据了喔~", Toast.LENGTH_SHORT).show()
                    holder.tips.text = "无更多数据了喔~"
                    holder.tips.visibility = View.VISIBLE
                    // 将fadeTips设置true
                    fadeTips = true
                    // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                    hasMore = true
                }
            }
        } else {
            onBindNormalViewHolder(holder, position)
        }
    }

    abstract fun setNormalHolder(parent: ViewGroup?, viewType: Int): ViewHolder

    abstract fun onBindNormalViewHolder(
        holder: ViewHolder?,
        position: Int
    )

    // 暴露接口，改变fadeTips的方法
    open fun isFadeTips(): Boolean {
        return fadeTips
    }


    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    open fun resetDatas() {
        datas.clear()
        notifyDataSetChanged()
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    open fun updateList(
        newDatas: MutableList<VH>?,
        hasMore: Boolean
    ) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            datas.addAll(newDatas)
        }
        this.hasMore = hasMore
        notifyDataSetChanged()
    }

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder(itemView: ViewDataBinding) :
        ViewHolder(itemView.root) {
        val tips: TextView

        init {
            val itemBinding = itemView as LayoutFootviewBinding
            tips = itemBinding.tips
        }
    }
}
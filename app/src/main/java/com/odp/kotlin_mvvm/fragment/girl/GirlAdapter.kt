package com.odp.kotlin_mvvm.fragment.girl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.bean.GankIoEntity
import com.odp.kotlin_mvvm.databinding.ItemFragementGirlBinding
import com.odp.kotlin_mvvm.util.LoadMoreAdapter

/**
 * @author  ChenHh
 * @time   2020/8/18 10:15
 * @des
 **/
class GirlAdapter(layoutManager: GridLayoutManager?) : LoadMoreAdapter<GankIoEntity>() {
    private lateinit var wealListener: IWealItemListener
    var girlLayoutManager: GridLayoutManager? = null

    init {
        girlLayoutManager = layoutManager
    }

    override fun getSubItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun setNormalHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return NormalHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.item_fragement_girl, parent, false
            )
        )
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val normalHolder = holder as NormalHolder
        normalHolder.bindData(position, datas)
    }

    fun setDataList(bean: MutableList<GankIoEntity>?) {
        if (bean != null) {
            datas = bean
            notifyDataSetChanged()
        }
    }

    class NormalHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val itemBinding: ItemFragementGirlBinding = itemView as ItemFragementGirlBinding

        fun bindData(position: Int, list: List<GankIoEntity>) {
            val gankIoEntity = list[position]
            Glide.with(itemView.context)
                .load(gankIoEntity.url)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.load_err)
                .into(itemBinding.ivWeal)
        }
    }

    fun updateData(list: MutableList<GankIoEntity>?, refresh: Boolean) {
        if (list != null) {
            loadMoreData(list, refresh)
        }
    }

    fun setItemClickListener(listener: IWealItemListener) {
        wealListener = listener
    }

    interface IWealItemListener {
        fun onItemListener(str: String?, view: View?)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (hasMore && girlLayoutManager != null) {
            girlLayoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    // 当position为最后一项时返回spanCount
                    return if (position == itemCount - 1) girlLayoutManager!!.spanCount else 1
                }
            }
        }
    }
}
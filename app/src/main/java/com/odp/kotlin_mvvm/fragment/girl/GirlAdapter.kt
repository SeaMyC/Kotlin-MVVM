package com.odp.kotlin_mvvm.fragment.girl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
class GirlAdapter : LoadMoreAdapter<MutableList<GankIoEntity>>() {
    private lateinit var wealListener: IWealItemListener
    private  var dataList: List<GankIoEntity> = mutableListOf()

    override fun getSubItemCount(): Int {
        return dataList.size
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
        normalHolder.bindData(position, dataList)
    }

    fun setDatas(bean: List<GankIoEntity>?) {
        if (bean != null) {
            dataList = bean
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

    fun setItemClickListener(listener: IWealItemListener) {
        wealListener = listener
    }

    interface IWealItemListener {
        fun onItemListener(str: String?, view: View?)
    }
}
package com.odp.kotlin_mvvm.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.databinding.ItemFragementSportBinding
import com.odp.kotlin_mvvm.util.LoadMoreAdapter
import com.odp.kotlin_mvvm.util.ScreenUtils
import kotlin.math.roundToInt

/**
 * @author  ChenHh
 * @time   2020/8/18 10:15
 * @des
 **/
class NewsAdapter : LoadMoreAdapter<BannerEntity>() {
    private lateinit var wealListener: IWealItemListener


    override fun getSubItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun setNormalHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return NormalHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent!!.context),
                R.layout.item_fragement_sport, parent, false
            )
        )
    }

    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val normalHolder = holder as NormalHolder
        normalHolder.bindData(position, datas, wealListener)
    }

    fun setDataList(bean: MutableList<BannerEntity>?) {
        if (bean != null) {
            datas = bean
            notifyDataSetChanged()
        }
    }

    private class NormalHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val itemBinding: ItemFragementSportBinding = itemView as ItemFragementSportBinding

        fun bindData(position: Int, list: List<BannerEntity>, listener: IWealItemListener) {
            val bannerEntity = list[position]
            itemBinding.llImage.removeAllViews()

            itemBinding.tvTitle.text = bannerEntity.title
            val screenUtils = ScreenUtils();
            val screenWidth = screenUtils.getScreenWidth(itemView.context)
            val width = (screenWidth - screenUtils.dpToPx(itemView.context, 40f)) / 3

            val ivOne = ImageView(itemView.context)
            val layoutParamsOne = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParamsOne.marginEnd = 15
            layoutParamsOne.width= width.roundToInt()
            ivOne.layoutParams = layoutParamsOne
            Glide.with(itemView.context)
                .load(bannerEntity.pic1)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.load_err)
                .into(ivOne)
            itemBinding.llImage.addView(ivOne)

            val ivTwo = ImageView(itemView.context)
            val layoutParamsTwo = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParamsTwo.marginEnd = 15
            layoutParamsTwo.width= width.roundToInt()
            ivTwo.layoutParams = layoutParamsTwo
            Glide.with(itemView.context)
                .load(bannerEntity.pic2)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.load_err)
                .into(ivTwo)
            itemBinding.llImage.addView(ivTwo)

            val ivThree = ImageView(itemView.context)
            val layoutParamsThree = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            ivThree.layoutParams = layoutParamsThree
            layoutParamsThree.width= width.roundToInt()
            Glide.with(itemView.context)
                .load(bannerEntity.pic3)
                .placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.load_err)
                .into(ivThree)
            itemBinding.llImage.addView(ivThree)


            itemBinding.rlView.setOnClickListener {
                listener.onItemListener(bannerEntity.url, itemBinding.rlView)
            }
        }
    }


    fun setItemClickListener(listener: IWealItemListener) {
        this.wealListener = listener
    }

    interface IWealItemListener {
        fun onItemListener(str: String?, view: View?)
    }


}
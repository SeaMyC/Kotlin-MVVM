package com.odp.kotlin_mvvm.fragment.main

import android.annotation.SuppressLint
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
import com.odp.kotlin_mvvm.databinding.ItemFragementSportSimpleBinding
import com.odp.kotlin_mvvm.util.CustomRoundImageView
import com.odp.kotlin_mvvm.util.ScreenUtils
import kotlinx.android.synthetic.main.item_fragement_sport_simple.view.*
import kotlin.math.roundToInt

/**
 * @author  ChenHh
 * @time   2020/8/18 10:15
 * @des
 **/
class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataS = mutableListOf<BannerEntity>()
    lateinit var wealListener: IWealItemListener
    private val MULTIPLE_IMAGE: Int = 0
    private val SIMPLE_IMAGE: Int = 1

    override fun getItemCount(): Int {
        return dataS.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalHolder) {
            holder.bindData(position)
        } else {
            val simpleHolder = holder as SimpleHolder
            simpleHolder.bindData(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!dataS.isNullOrEmpty() && dataS[position].pic2.isNullOrEmpty()) SIMPLE_IMAGE
        else MULTIPLE_IMAGE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MULTIPLE_IMAGE) NormalHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_fragement_sport, parent, false
            )
        )
        else
            SimpleHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_fragement_sport_simple, parent, false
                )
            )
    }

    fun setDataList(bean: MutableList<BannerEntity>?) {
        if (bean != null) {
            this.dataS = bean
            notifyDataSetChanged()
        }
    }

    inner class NormalHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val itemBinding: ItemFragementSportBinding = itemView as ItemFragementSportBinding

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            if (!dataS.isNullOrEmpty()) {
                val bannerEntity = dataS[position]
                itemBinding.tvTitle.text = bannerEntity.title
                itemBinding.tvDesc.text =
                    StringBuffer().append(bannerEntity.authorName).append("  ")
                        .append(bannerEntity.date)
                itemBinding.tvDelete.setOnClickListener {
                    dataS.removeAt(position)
                    notifyDataSetChanged()
                }

                itemBinding.llImage.removeAllViews()
                val screenUtils = ScreenUtils()
                val screenWidth = screenUtils.getScreenWidth(itemView.context)
                val width = (screenWidth - screenUtils.dpToPx(itemView.context, 40f)) / 3

                val ivOne = CustomRoundImageView(itemView.context)
                ivOne.setAllRadius(10)
                val layoutParamsOne = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParamsOne.marginEnd = 15
                layoutParamsOne.width = width.roundToInt()
                ivOne.layoutParams = layoutParamsOne
                Glide.with(itemView.context)
                    .load(bannerEntity.pic1)
                    .placeholder(R.drawable.img_default_meizi)
                    .error(R.drawable.load_err)
                    .into(ivOne)
                itemBinding.llImage.addView(ivOne)

                val ivTwo = CustomRoundImageView(itemView.context)
                ivTwo.setAllRadius(10)
                val layoutParamsTwo = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParamsTwo.marginEnd = 15
                layoutParamsTwo.width = width.roundToInt()
                ivTwo.layoutParams = layoutParamsTwo
                Glide.with(itemView.context)
                    .load(bannerEntity.pic2)
                    .placeholder(R.drawable.img_default_meizi)
                    .error(R.drawable.load_err)
                    .into(ivTwo)
                itemBinding.llImage.addView(ivTwo)

                if (!bannerEntity.pic3.isNullOrEmpty()) {
                    val ivThree = CustomRoundImageView(itemView.context)
                    ivThree.setAllRadius(10)
                    val layoutParamsThree = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    ivThree.layoutParams = layoutParamsThree
                    layoutParamsThree.width = width.roundToInt()
                    Glide.with(itemView.context)
                        .load(bannerEntity.pic3)
                        .placeholder(R.drawable.img_default_meizi)
                        .error(R.drawable.load_err)
                        .into(ivThree)
                    itemBinding.llImage.addView(ivThree)
                }


                itemBinding.rlView.setOnClickListener {
                    wealListener.onItemListener(bannerEntity.url, itemBinding.rlView)
                }
            }
        }
    }

    inner class SimpleHolder(itemView: ViewDataBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val itemBinding: ItemFragementSportSimpleBinding =
            itemView as ItemFragementSportSimpleBinding

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {
            if (!dataS.isNullOrEmpty()) {
                val bannerEntity = dataS[position]
                itemBinding.tvTitle.text = bannerEntity.title
                itemBinding.tvDesc.text =
                    StringBuffer().append(bannerEntity.authorName).append("  ")
                        .append(bannerEntity.date)
                itemBinding.tvDelete.setOnClickListener {
                    dataS.removeAt(position)
                    notifyDataSetChanged()
                }

                Glide.with(itemView.context)
                    .load(bannerEntity.pic1)
                    .placeholder(R.drawable.img_default_meizi)
                    .error(R.drawable.load_err)
                    .into(itemView.iv_pic)


                itemBinding.rlView.setOnClickListener {
                    wealListener.onItemListener(bannerEntity.url, itemBinding.rlView)
                }
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
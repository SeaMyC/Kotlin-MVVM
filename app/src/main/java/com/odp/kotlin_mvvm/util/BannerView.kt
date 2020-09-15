package com.odp.kotlin_mvvm.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.bean.BannerEntity


/**
 * @author  ChenHh
 * @time   2020/9/2 14:33
 * @des
 **/
class BannerView : FrameLayout {
    private var lastPosition: Int = 0
    private var llPoints: LinearLayout
    private var viewPager: ViewPager2

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_banner_view, this, true)
        viewPager = view.findViewById(R.id.banner_viewPager)
        llPoints = view.findViewById(R.id.ll_points)
    }


    fun setData(list: List<BannerEntity>) {

        val viewPagerAdapter = ViewPagerAdapter()
        viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.setData(list)
        llPoints.removeAllViews()
        if (list.isNotEmpty()) {
            for (i in list.indices) {
                val imageView = ImageView(context)
                if (i == 0) imageView.setBackgroundResource(R.drawable.shape_banner_white_point) else imageView.setBackgroundResource(
                    R.drawable.shape_banner_grey_point
                )
                //为指示点添加间距
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.marginEnd = 12
                imageView.layoutParams = layoutParams
                //将指示点添加进容器
                llPoints.addView(imageView)
            }
        }

        viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //轮播时，改变指示点
                val current = position % list.size
                val last: Int = lastPosition % list.size
                llPoints.getChildAt(current)
                    .setBackgroundResource(R.drawable.shape_banner_white_point)
                if (position != 0) {
                    llPoints.getChildAt(last)
                        .setBackgroundResource(R.drawable.shape_banner_grey_point)
                    lastPosition = position
                }
            }
        })

    }

    private class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

        private var dataList: List<BannerEntity> = listOf()

        fun setData(list: List<BannerEntity>) {
            dataList = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_banner_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindData(dataList[position % dataList.size])
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var imageView: ImageView = itemView.findViewById(R.id.imageView)
            private var title: TextView = itemView.findViewById(R.id.tv_title)

            fun bindData(entity: BannerEntity) {
                if (entity.pic1 != null) {
                    Glide.with(itemView.context)
                        .load(entity.pic1)
                        .placeholder(R.drawable.img_default_meizi)
                        .error(R.drawable.load_err)
                        .into(imageView)
                }

                title.text = entity.title
            }
        }
    }
}
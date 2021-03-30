package com.odp.kotlin_mvvm.fragment.movie

import android.annotation.SuppressLint
import android.os.Build
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.bean.BannerEntity
import com.odp.kotlin_mvvm.bean.HotMovieEntity
import com.odp.kotlin_mvvm.databinding.ItemFragementSportBinding
import com.odp.kotlin_mvvm.databinding.ItemHotMovieBinding
import com.odp.kotlin_mvvm.util.CustomRoundImageView
import com.odp.kotlin_mvvm.util.ImgSizeUtil
import com.odp.kotlin_mvvm.util.ScreenUtils
import java.util.*
import kotlin.math.roundToInt

/**
 * @author  ChenHh
 * @time   2021/3/29 14:08
 * @des
 **/
class HotMovieAdapter : RecyclerView.Adapter<HotMovieAdapter.MovieViewHolder>() {
    var dataS = mutableListOf<HotMovieEntity>()
    lateinit var movieListener: IMovieItemListener

    override fun getItemCount(): Int {
        return dataS.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: HotMovieAdapter.MovieViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HotMovieAdapter.MovieViewHolder {
        return MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_hot_movie, parent, false
            )
        )
    }

    fun setDataList(bean: MutableList<HotMovieEntity>?) {
        if (bean != null) {
            this.dataS = bean
            notifyDataSetChanged()
        }
    }

    inner class MovieViewHolder(itemView: ViewDataBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val itemBinding: ItemHotMovieBinding = itemView as ItemHotMovieBinding

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bindData(position: Int) {
            if (!dataS.isNullOrEmpty()) {
                val hotMovieEntity = dataS[position]

                itemBinding.tvName.text = hotMovieEntity.nm

                if (hotMovieEntity.img.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(
                            ImgSizeUtil().resetPicUrl(
                                hotMovieEntity.img,
                                ".webp@171w_240h_1e_1c_1l"
                            )
                        )
                        .placeholder(R.drawable.img_default_meizi)
                        .error(R.drawable.load_err)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                        .into(itemBinding.ivMovieBg)
                }
                if (hotMovieEntity.preSale == 1) {
                    itemBinding.tvBuy.background =
                        itemView.context.getDrawable(R.drawable.shape_movie_buy_btn_blue_bg)
                    itemBinding.tvBuy.text = "预售"
                    itemBinding.tvScore.setTextColor(itemView.context.getColor(R.color.white))
                    itemBinding.tvScore.text = hotMovieEntity.wish.toString() + " 想看"

                } else {
                    itemBinding.tvBuy.background =
                        itemView.context.getDrawable(R.drawable.shape_movie_buy_btn_red_bg)
                    itemBinding.tvBuy.text = "购票"
                    itemBinding.tvScore.text = hotMovieEntity.mk.toString() + " 分"
                    itemBinding.tvScore.setTextColor(itemView.context.getColor(R.color.color_score))


                }
                setTag(hotMovieEntity)

//                itemBinding.root.setOnClickListener {
//                    movieListener.onItemListener(bannerEntity.url, itemBinding.rlView)
//                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun MovieViewHolder.setTag(hotMovieEntity: HotMovieEntity) {
        itemBinding.llTag.removeAllViews()
        if (hotMovieEntity.ver.contains("杜比")) {
            val duBiTv = TextView(itemView.context)
            duBiTv.text = "杜比影院"
            duBiTv.textSize = 8f
            duBiTv.setBackgroundColor(itemView.context.getColor(R.color.color_black))
            duBiTv.setTextColor(itemView.context.getColor(R.color.white))

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 4
            duBiTv.layoutParams = layoutParams
            itemBinding.llTag.addView(duBiTv)
        }

        if (hotMovieEntity.ver.contains("中国巨幕")) {
            val duBiTv = TextView(itemView.context)
            duBiTv.text = "中国巨幕"
            duBiTv.textSize = 8f
            duBiTv.setBackgroundColor(itemView.context.getColor(R.color.color_black))
            duBiTv.setTextColor(itemView.context.getColor(R.color.white))

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 4
            duBiTv.layoutParams = layoutParams
            itemBinding.llTag.addView(duBiTv)
        }
        if (hotMovieEntity.ver.contains("IMAX 3D")) {
            val duBiTv = TextView(itemView.context)
            duBiTv.text = "IMAX 3D"
            duBiTv.textSize = 8f
            duBiTv.setBackgroundColor(itemView.context.getColor(R.color.color_black))
            duBiTv.setTextColor(itemView.context.getColor(R.color.white))

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 4
            duBiTv.layoutParams = layoutParams
            itemBinding.llTag.addView(duBiTv)
        }
        if (hotMovieEntity.ver.contains("CINITY 2D")) {
            val duBiTv = TextView(itemView.context)
            duBiTv.text = "IMAX 2D"
            duBiTv.textSize = 8f
            duBiTv.setBackgroundColor(itemView.context.getColor(R.color.color_black))
            duBiTv.setTextColor(itemView.context.getColor(R.color.white))

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.topMargin = 4
            duBiTv.layoutParams = layoutParams
            itemBinding.llTag.addView(duBiTv)
        }
    }


    fun setItemClickListener(listener: IMovieItemListener) {
        this.movieListener = listener
    }

    interface IMovieItemListener {
        fun onItemListener(str: String?, view: View?)
    }
}
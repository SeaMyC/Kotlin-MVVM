package com.odp.kotlin_mvvm.fragment.movie

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dueeeke.videocontroller.StandardVideoController
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.bean.DiscoverMovieEntity
import com.odp.kotlin_mvvm.databinding.ItemDiscoverMovieBinding
import com.odp.kotlin_mvvm.util.ImgSizeUtil

/**
 * @author  ChenHh
 * @time   2021/3/29 14:08
 * @des
 **/
class DiscoverMovieAdapter : RecyclerView.Adapter<DiscoverMovieAdapter.MovieViewHolder>() {
    var dataS = mutableListOf<DiscoverMovieEntity>()
    lateinit var movieListener: IMovieItemListener

    override fun getItemCount(): Int {
        return dataS.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: DiscoverMovieAdapter.MovieViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverMovieAdapter.MovieViewHolder {
        return MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_discover_movie, parent, false
            )
        )
    }

    fun setDataList(bean: MutableList<DiscoverMovieEntity>?) {
        if (bean != null) {
            this.dataS = bean
            notifyDataSetChanged()
        }
    }

    inner class MovieViewHolder(itemView: ViewDataBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val itemBinding: ItemDiscoverMovieBinding = itemView as ItemDiscoverMovieBinding

        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bindData(position: Int) {
            if (!dataS.isNullOrEmpty()) {
                val discoverMovieEntity = dataS[position]

                itemBinding.ivMovieVv.setUrl(discoverMovieEntity.url)
                val controller = StandardVideoController(itemView.context)
                controller.addDefaultControlComponent("标题", false)
                itemBinding.ivMovieVv.setVideoController(controller) //设置控制器

                itemBinding.tvName.text = discoverMovieEntity.originName
                if (discoverMovieEntity.img.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(discoverMovieEntity.img)
                        .placeholder(R.drawable.img_default_meizi)
                        .error(R.drawable.load_err)
                        .into(itemBinding.ivPre)
                }

                itemBinding.ivPlay.setOnClickListener {
                    itemBinding.ivMovieVv.start()
                    itemBinding.ivPlay.visibility = View.GONE
                    itemBinding.ivPre.visibility = View.GONE
                    itemBinding.tvName.visibility = View.GONE
                }

                if (itemBinding.ivMovieVv.isPlaying) {
                    itemBinding.ivPlay.visibility = View.GONE
                    itemBinding.ivPre.visibility = View.GONE
                    itemBinding.tvName.visibility = View.GONE
                }else{
                    itemBinding.ivPlay.visibility = View.VISIBLE
                    itemBinding.ivPre.visibility = View.VISIBLE
                    itemBinding.tvName.visibility = View.VISIBLE

                }

            }
        }
    }


    fun setItemClickListener(listener: IMovieItemListener) {
        this.movieListener = listener
    }

    interface IMovieItemListener {
        fun onItemListener(str: String?, view: View?)
    }
}
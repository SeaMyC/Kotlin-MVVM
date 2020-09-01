package com.odp.kotlin_mvvm.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.databinding.ActivityGirlBinding

/**
 * @author  ChenHh
 * @time   2020/8/25 16:10
 * @des
 **/
class GirlActivity : BinDingActivity<ActivityGirlBinding>() {
    override fun getLayout(): Int {
        return R.layout.activity_girl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("girl_image_url")

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.img_default_meizi)
            .error(R.drawable.load_err)
            .into(bindingView.imageView)
    }
}
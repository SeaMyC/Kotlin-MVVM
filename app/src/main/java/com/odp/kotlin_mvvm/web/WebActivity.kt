package com.odp.kotlin_mvvm.web

import android.annotation.SuppressLint
import android.os.Bundle
import com.odp.kotlin_mvvm.R
import com.odp.kotlin_mvvm.base.BinDingActivity
import com.odp.kotlin_mvvm.databinding.ActivityWebBinding

/**
 * @author  ChenHh
 * @time   2020/9/17 15:19
 * @des  webActivity
 **/
class WebActivity : BinDingActivity<ActivityWebBinding>() {

    override fun getLayout(): Int {
        return R.layout.activity_web
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webSettings = bindingView.webView.settings;

        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setAppCacheEnabled(true)

        val url = intent.getStringExtra("web_url")
        bindingView.webView.loadUrl(url)

    }
}
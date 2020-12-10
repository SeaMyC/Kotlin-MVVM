package com.odp.kotlin_mvvm.fragment.news

import androidx.core.app.ActivityCompat
import com.odp.kotlin_mvvm.config.BANNER_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.file.attribute.AclEntry


object RetrofitClient {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BANNER_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)

}
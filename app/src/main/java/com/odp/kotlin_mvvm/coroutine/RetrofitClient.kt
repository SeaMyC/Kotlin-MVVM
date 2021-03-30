package com.odp.kotlin_mvvm.coroutine

import com.odp.kotlin_mvvm.config.BANNER_API_URL
import com.odp.kotlin_mvvm.config.MOVIE_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BANNER_API_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
        )
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    private var movieRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(MOVIE_API_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build()
        )
        .build()

    fun <T> createMovie(service: Class<T>): T = movieRetrofit.create(service)

}
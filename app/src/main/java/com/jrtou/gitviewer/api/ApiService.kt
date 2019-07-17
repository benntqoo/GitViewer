package com.jrtou.gitviewer.api

import android.util.Log
import com.jrtou.gitviewer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiService private constructor() {
    companion object {
        const val API_USER = "users/"

        private const val API_URL = "https://api.github.com/"
        val instance by lazy { Holder.INSTANCE }


        private val TAG: String = ApiService::class.java.simpleName
    }


    private object Holder {
        val INSTANCE = ApiService()
    }

    lateinit var service: ApiInterface

    fun init() {
        Log.i(TAG, "init (line 44): url = $API_URL")
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(createClient())
            .build()

        service = retrofit.create(ApiInterface::class.java)
    }

    private fun createClient(): OkHttpClient {
        val loggerLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(loggerLevel))
            .build()
    }
}
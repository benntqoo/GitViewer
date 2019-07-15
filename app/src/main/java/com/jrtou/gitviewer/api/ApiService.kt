package com.jrtou.gitviewer.api

import android.util.Log
import com.jrtou.gitviewer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class ApiService private constructor() {

    private object Holder {
        val INSTANCE = ApiService()
    }

    companion object {
        private const val API_URL = ""
        val instance by lazy { Holder.INSTANCE }


        private val TAG: String = ApiService::class.java.simpleName
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
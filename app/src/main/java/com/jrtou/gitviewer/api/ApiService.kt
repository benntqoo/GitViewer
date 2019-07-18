package com.jrtou.gitviewer.api

import android.util.Log
import com.jrtou.gitviewer.BuildConfig
import com.jrtou.gitviewer.api.interfaces.ApiInterface
import com.jrtou.gitviewer.api.interfaces.TrendInterface
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiService private constructor() {
    companion object {
        private val TAG: String = ApiService::class.java.simpleName

        const val API_SEARCH = "search/"
        const val API_USER = "users/"

        const val API_REPO = "repositories"

        private const val API_URL = "https://api.github.com/"
        private const val TREND_URL = "https://github-trending-api.now.sh/"
        val instance by lazy { Holder.INSTANCE }
    }

    /**
     * Api 請求 [Observable][io.reactivex.Observable]中[操作符][io.reactivex.Observable.compose] 轉換參數
     */
    object ComposeUtil {
        /**
         * [RxActivity][RxAppCompatActivity]使用 [操作符][io.reactivex.Observable.compose] 轉換
         *
         * thread 切換 在[subscribeOn][io.reactivex.Observable.subscribeOn]切換 [io thread][io.reactivex.schedulers.Schedulers.io],
         * [observeOn][io.reactivex.Observable.observeOn]切換[main thread][io.reactivex.android.schedulers.AndroidSchedulers.mainThread]
         *
         * 與 activity 生命週期綁定
         *
         * @param activity [RxAppCompatActivity]
         * @param activityEvent [ActivityEvent]
         */
        fun <T> activityNetworkCompose(activity: RxAppCompatActivity, activityEvent: ActivityEvent): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindUntilEvent(activity, activityEvent)
            }
        }

        /**
         * [RxFragment][RxFragment]使用 [操作符][io.reactivex.Observable.compose] 轉換
         *
         * thread 切換 在[subscribeOn][io.reactivex.Observable.subscribeOn]切換 [io thread][io.reactivex.schedulers.Schedulers.io],
         * [observeOn][io.reactivex.Observable.observeOn]切換[main thread][io.reactivex.android.schedulers.AndroidSchedulers.mainThread]
         *
         * 與 fragment 生命週期綁定
         *
         * @param fragment [RxFragment]
         * @param fragmentEvent [FragmentEvent]
         */
        fun <T> fragmentNetWorkCompose(fragment: RxFragment, fragmentEvent: FragmentEvent): ObservableTransformer<T, T> {
            return ObservableTransformer {
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindUntilEvent(fragment, fragmentEvent)
            }
        }

        /**
         * thread 切換 在[subscribeOn][io.reactivex.Observable.subscribeOn]切換 [io thread][io.reactivex.schedulers.Schedulers.io],
         * [observeOn][io.reactivex.Observable.observeOn]切換[main thread][io.reactivex.android.schedulers.AndroidSchedulers.mainThread]
         */
        fun <T> networkSchedulerCompose(): ObservableTransformer<T, T> {
            return ObservableTransformer { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }
    }

    private object Holder {
        val INSTANCE = ApiService()
    }

    lateinit var service: ApiInterface
    lateinit var trendService: TrendInterface

    fun init() {
        Log.i(TAG, "init (line 44): url = $API_URL")
        val retrofit = createRetrofit(API_URL)
        service = retrofit.create(ApiInterface::class.java)

        val trendRetrofit = createRetrofit(TREND_URL)
        trendService = trendRetrofit.create(TrendInterface::class.java)
    }

    private fun createClient(): OkHttpClient {
        val loggerLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = loggerLevel })
            .build()
    }

    private fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(createClient())
            .build()
    }
}
package com.jrtou.gitviewer.api

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ApiManager {
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
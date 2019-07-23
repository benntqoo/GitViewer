package com.jrtou.gitviewer.framework.models

import androidx.lifecycle.LiveData
import com.jrtou.gitviewer.api.ApiService
import com.jrtou.gitviewer.api.GitHubData
import com.jrtou.gitviewer.framework.NetworkBoundResource
import io.reactivex.Observable

class TrendRepository {
    companion object {
        private const val TAG = "TrendRepository"
        val SINGLETON by lazy { Holder.singleton }
    }

    private object Holder {
        val singleton = TrendRepository()
    }


    fun getTrend(): LiveData<MutableList<GitHubData.TrendItem>> {
        return object : NetworkBoundResource<MutableList<GitHubData.TrendItem>, MutableList<GitHubData.TrendItem>>() {
            override fun saveCallResult(item: MutableList<GitHubData.TrendItem>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun shouldFetch(data: MutableList<GitHubData.TrendItem>?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun loadFromDb(): LiveData<MutableList<GitHubData.TrendItem>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun createCall(): Observable<MutableList<GitHubData.TrendItem>> {
                return ApiService.instance.trendService.getPopRepo()
            }
        }.asLiveData()
    }
}
package com.jrtou.gitviewer.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.api.*
import com.jrtou.gitviewer.views.activities.MainActivity
import com.jrtou.gitviewer.views.adapters.MainAdapter
import com.trello.rxlifecycle2.android.FragmentEvent
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : RxSupportSHAbstractFragment<MainActivity>() {
    companion object {
        private const val TAG = "HomeFragment"

        fun newInstance(): Fragment {
            return HomeFragment()
        }
    }


    private val adapter = MainAdapter()
    override fun onInflaterView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewSetting(activity: MainActivity) {
        rvMain.layoutManager = LinearLayoutManager(activity)
        rvMain.adapter = adapter
    }

    override fun onRenderView(activity: MainActivity, savedInstanceState: Bundle?) {
        getPop()
    }

    override fun onShow(activity: MainActivity) {
    }

    override fun onHidden(activity: MainActivity) {
    }

    override fun onRestoreState(savedInstanceState: Bundle) {
    }

    override fun onSaveState(outState: Bundle) {
    }

    private fun getPop() {
        mActivity?.let { activity ->
            ApiService.instance.trendService.getPopRepo()
                .compose(ApiManager.fragmentNetWorkCompose(this, FragmentEvent.PAUSE))
                .subscribe(object : ApiResponse<MutableList<GitHubData.TrendItem>>(activity) {
                    override fun success(data: MutableList<GitHubData.TrendItem>, result: ApiResult.Model) {
                        adapter.items.clear()
                        adapter.items.addAll(data)
                    }

                    override fun failure(statusCode: String, model: ApiResult.Model) {
                    }
                })
        }
    }

    private fun getRepo() {
        mActivity?.let { activity ->
            ApiService.instance.service.searchRepoByKeyWord(" ")
                .compose(ApiManager.fragmentNetWorkCompose(this, FragmentEvent.PAUSE))
                .subscribe(object : ApiResponse<GitHubData.Repo>(activity) {
                    override fun success(data: GitHubData.Repo, result: ApiResult.Model) {

                    }

                    override fun failure(statusCode: String, model: ApiResult.Model) {
                        Log.i(TAG, "failure (line 64): $statusCode  model = $model")
                    }
                })
        }
    }
}



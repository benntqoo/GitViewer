package com.jrtou.gitviewer.views.activities

import android.os.Bundle
import android.util.Log
import com.jrtou.gitviewer.R
import com.jrtou.gitviewer.api.ApiManager
import com.jrtou.gitviewer.api.ApiResult
import com.jrtou.gitviewer.api.ApiService
import com.jrtou.gitviewer.api.ApiResponse
import com.jrtou.gitviewer.api.GitHubData
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiService.instance.service.getUserInfo("benntqoo")
            .compose(ApiManager.activityNetworkCompose(this, ActivityEvent.PAUSE))
            .subscribe(object : ApiResponse<GitHubData.User>(this@MainActivity) {
                override fun success(data: GitHubData.User, result: ApiResult.Model) {
                    Log.i(TAG, "success (line 25): $result")
                }

                override fun failure(statusCode: String, model: ApiResult.Model) {
                    Log.i(TAG, "failure (line 30): $model")
                }
            })
    }
}


package com.jrtou.gitviewer

import android.app.Application
import com.jrtou.gitviewer.api.ApiService
import com.jrtou.gitviewer.databases.DatabaseHelper

class GVApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiService.instance.init()
        DatabaseHelper.init(this)
    }
}
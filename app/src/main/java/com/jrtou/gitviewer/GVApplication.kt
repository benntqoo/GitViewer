package com.jrtou.gitviewer

import android.app.Application
import com.jrtou.gitviewer.api.ApiService

class GVApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiService.instance.init()
    }
}
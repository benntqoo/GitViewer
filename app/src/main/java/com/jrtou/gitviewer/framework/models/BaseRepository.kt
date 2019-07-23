package com.jrtou.gitviewer.framework.models

import androidx.lifecycle.LiveData

/**
 * 參考 https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
 *
 * 資料加載使用基本架構
 */
abstract class BaseRepository<Result> {

    /**
     *
     */
    abstract fun loadFromDb(): LiveData<Result>

}
package com.jrtou.gitviewer.framework.databingUtils


object TrendUtil {
    private const val TAG = "TrendUtil"

    @JvmStatic
    fun getRepoName(author: String?, name: String?): String {
        return "$author/$name"
    }
}
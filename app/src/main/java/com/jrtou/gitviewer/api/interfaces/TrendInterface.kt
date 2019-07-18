package com.jrtou.gitviewer.api.interfaces

import com.jrtou.gitviewer.api.ApiService
import com.jrtou.gitviewer.api.GitHubData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface TrendInterface {
    @GET(ApiService.API_REPO)
    @Headers("Content-Type:application/json; charset=utf-8")
    fun getPopRepo(): Observable<MutableList<GitHubData.TrendItem>>
}
package com.jrtou.gitviewer.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiInterface {
    @GET(ApiService.API_USER + "{userId}")
    @Headers("Content-Type:application/json; charset=utf-8")
    fun getUserInfo(@Path("userId") userId: String): Observable<GitHubData.User>
}
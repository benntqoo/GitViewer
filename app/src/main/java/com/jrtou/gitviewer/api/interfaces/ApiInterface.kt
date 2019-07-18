package com.jrtou.gitviewer.api.interfaces

import com.jrtou.gitviewer.api.ApiQuery
import com.jrtou.gitviewer.api.ApiService
import com.jrtou.gitviewer.api.GitHubData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiService.API_SEARCH + "repositories")
    @Headers("Content-Type:application/json; charset=utf-8")
    fun searchRepoByKeyWord(@Query("q") keyword: String,
                            @Query("sort") sort: String = "stars",
                            @Query("order") order: String = "desc"): Observable<GitHubData.Repo>

    @GET(ApiService.API_SEARCH + "repositories/")
    @Headers("Content-Type:application/json; charset=utf-8")
    fun searchRepoByLang(@Query("q") queryStr: ApiQuery.SearchRepo)

    @GET(ApiService.API_USER + "{userId}")
    @Headers("Content-Type:application/json; charset=utf-8")
    fun getUserInfo(@Path("userId") userId: String): Observable<GitHubData.User>
}
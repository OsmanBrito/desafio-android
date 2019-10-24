package com.osman.bestjava.api

import com.osman.bestjava.data.entity.PullRequest
import com.osman.bestjava.data.entity.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitHubApi {

    @GET("search/repositories")
    suspend fun repositories(@Query("q") q: String, @Query("sort") sort: String, @Query("page") page: Int): Response<ResponseApi>

    @GET("repos/{owner}/{repo}/pulls")
    suspend fun pullRequests(@Path("owner") owner: String, @Path("repo") repo: String): Response<List<PullRequest>>

}
package com.osman.bestjava.api

import com.osman.bestjava.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    companion object {
        val githubApi: GitHubApi by lazy {
            Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(GitHubApi::class.java)
        }
    }
}
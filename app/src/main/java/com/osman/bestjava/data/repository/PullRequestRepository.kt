package com.osman.bestjava.data.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.osman.bestjava.api.RetrofitInitializer
import com.osman.bestjava.data.ProjectDataBase
import com.osman.bestjava.data.dao.PullRequestDAO
import com.osman.bestjava.data.entity.PullRequest

class PullRequestRepository constructor(private val application: Application) {

    private val dao: PullRequestDAO
    private val allPullRequest: LiveData<List<PullRequest>>

    init {
        val db = ProjectDataBase.getDatabase(application)
        dao = db!!.pullRequestDAO()
        allPullRequest = dao.pullRequests
    }

    suspend fun getPullRequests(owner: String, repo: String): List<PullRequest> {
        val response = RetrofitInitializer.githubApi.pullRequests(owner, repo)
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            allPullRequest.value!!
        }
    }

    companion object {
        private val TAG = "PullRequestRepository"
    }

}
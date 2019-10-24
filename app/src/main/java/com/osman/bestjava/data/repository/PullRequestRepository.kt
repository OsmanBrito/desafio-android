package com.osman.bestjava.data.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.osman.bestjava.RepositoryActivity
import com.osman.bestjava.api.RetrofitInitializer
import com.osman.bestjava.data.ProjectDataBase
import com.osman.bestjava.data.dao.PullRequestDAO
import com.osman.bestjava.data.entity.PullRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PullRequestRepository constructor(private val application: Application) {

    private val dao: PullRequestDAO
    private val allRepositories: LiveData<List<PullRequest>>

    init {
        val db = ProjectDataBase.getDatabase(application)
        dao = db!!.pullRequestDAO()
        allRepositories = dao.pullRequests
    }

    suspend fun getPullRequests(owner: String, repo: String): LiveData<List<PullRequest>> {
        if (dao.getAll().isNullOrEmpty()) {
            refreshRepositories(owner, repo)
        }
        return dao.pullRequests
    }

    private suspend fun refreshRepositories(owner: String, repo: String) {
        try {
            val response = RetrofitInitializer.githubApi.pullRequests(owner, repo)
            if (!response.isSuccessful) {
                dao.let { createAllAsyncTask(it).execute() }
            } else {
                //TODO:To implement else here
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private class createAllAsyncTask internal constructor(private val mAsyncTaskDao: PullRequestDAO) :
        AsyncTask<List<PullRequest>, Void, Void>() {

        override fun doInBackground(vararg params: List<PullRequest>): Void? {
            mAsyncTaskDao.createAll(params[0])
            Log.e(TAG, "Add pull requests.")
            return null
        }
    }

    companion object {
        private val TAG = "PullRequestRepository"
    }

}
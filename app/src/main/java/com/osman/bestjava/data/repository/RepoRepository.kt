package com.osman.bestjava.data.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import com.osman.bestjava.ui.view.RepositoryActivity
import com.osman.bestjava.api.RetrofitInitializer
import com.osman.bestjava.data.ProjectDataBase
import com.osman.bestjava.data.dao.RepositoryDAO
import com.osman.bestjava.data.entity.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoRepository constructor(private val application: Application) {

    private val dao: RepositoryDAO
    private val allRepositories: LiveData<List<Repository>>

    init {
        val db = ProjectDataBase.getDatabase(application)
        dao = db!!.repositoryDAO()
        allRepositories = dao.repositories
    }

    suspend fun getRepositories(nextPage: Boolean): LiveData<List<Repository>> {
        if (dao.getAll().isNullOrEmpty() || nextPage) {
            refreshRepositories()
        }
        return dao.repositories
    }

    private suspend fun refreshRepositories() {
        try {
            val response = RetrofitInitializer.githubApi.repositories(
                "language:java",
                "stars",
                if (dao.getAll().isNullOrEmpty()) 1 else (dao.getAll().size / 30) + 1
            )
            if (response.isSuccessful) {
                dao.let { createAllAsyncTask(it).execute(response.body()?.items) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            RepositoryActivity.semaphore = 0
            withContext(Dispatchers.Main) {
                RepositoryActivity.progressBar.visibility = View.GONE
            }
        }
    }

    private class createAllAsyncTask internal constructor(private val mAsyncTaskDao: RepositoryDAO) :
        AsyncTask<List<Repository>, Void, Void>() {

        override fun doInBackground(vararg params: List<Repository>): Void? {
            mAsyncTaskDao.createAll(params[0])
            Log.e(TAG, "Add repositories")
            return null
        }
    }

    companion object {
        private val TAG = "RepoRepository"
    }
}
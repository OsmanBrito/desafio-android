package com.osman.bestjava.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.osman.bestjava.data.entity.Repository
import com.osman.bestjava.data.repository.RepoRepository
import kotlinx.coroutines.Dispatchers

class RepositoriesViewModel(application: Application) : ViewModel() {

    private val repoRepository: RepoRepository = RepoRepository(application)
    internal var repositories: LiveData<List<Repository>> = MutableLiveData()

    init {
        repositories = liveData(Dispatchers.IO) {
            emitSource(repoRepository.getRepositories(false))
        }
    }

    suspend fun loadRepositories() {
        repoRepository.getRepositories(true)
    }
}
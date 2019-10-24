package com.osman.bestjava.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.osman.bestjava.data.entity.PullRequest
import com.osman.bestjava.data.repository.PullRequestRepository
import kotlinx.coroutines.Dispatchers

class PullRequestViewModel constructor(application: Application, owner: String, repo: String) :
    ViewModel() {

    private val pullRequestRepository: PullRequestRepository = PullRequestRepository(application)
    internal var pullRequests: LiveData<List<PullRequest>> = MutableLiveData()

    init {
        pullRequests = liveData(Dispatchers.IO) {
            emit(pullRequestRepository.getPullRequests(owner, repo))
        }
    }
}
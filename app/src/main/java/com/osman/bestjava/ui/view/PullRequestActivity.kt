package com.osman.bestjava.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.osman.bestjava.R
import com.osman.bestjava.data.entity.PullRequest
import com.osman.bestjava.ui.adapter.PullRequestAdapter
import com.osman.bestjava.viewmodel.PullRequestViewModel
import kotlinx.android.synthetic.main.activity_pull_request_list.*

class PullRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request_list)
        val layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = layoutManager
        val mViewModel = PullRequestViewModel(
            application,
            intent.getStringExtra("owner"),
            intent.getStringExtra("repo")
        )
        val adapter = PullRequestAdapter(this)
        recyclerView.adapter = adapter
        mViewModel.pullRequests.observe(this, Observer { pullRequests ->
            adapter.add(pullRequests as ArrayList<PullRequest>)
        })
    }

}
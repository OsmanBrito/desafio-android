package com.osman.bestjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.osman.bestjava.RepositoryActivity.Companion.progressBar
import com.osman.bestjava.data.entity.Repository
import com.osman.bestjava.viewmodel.RepositoriesViewModel
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryActivity : AppCompatActivity() {

    private lateinit var mViewModel: RepositoriesViewModel
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        layoutManager = LinearLayoutManager(this)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.layoutManager = layoutManager
        mViewModel = RepositoriesViewModel(application)
        val adapter = RepositoryAdapter(this)
        recyclerView.adapter = adapter
        Companion.progressBar = progressBar
        mViewModel.repositories.observe(this, Observer { repositories ->
            semaphore = 0
            progressBar.visibility = View.GONE
            adapter.add(repositories as ArrayList<Repository>)
        })
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                recyclerView.let { super.onScrollStateChanged(it, newState) }
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1 && semaphore == 0) {
                    semaphore++
                    Log.d("MyTAG", "Load new list")
                    progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        //                        delay(100)
                        mViewModel.loadRepositories()
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    companion object {
        lateinit var progressBar: ProgressBar
        var semaphore: Int = 0
        val TAG = "RepositoryActivity"
    }
}

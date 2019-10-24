package com.osman.bestjava.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osman.bestjava.R
import com.osman.bestjava.data.entity.Repository
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter (private val context: Context) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var mRepositories: ArrayList<Repository> = arrayListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(repo: Repository) {
            val name = itemView.repo_tv_name
            val stars = itemView.repo_tv_stars_count
            val forks = itemView.repo_tv_forks_count
            val ownerImage = itemView.repo_iv_owner_photo
            val ownerName = itemView.repo_tv_owner_name
            name.text = repo.name
            stars.text = repo.stargazersCount.toString()
            forks.text = repo.forksCount.toString()
            Glide.with(itemView).load(repo.owner.photo).into(ownerImage)
            ownerName.text = repo.owner.login
        }
    }

    fun add(repositories: ArrayList<Repository>) {
        mRepositories = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repository_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mRepositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mRepositories[position])
    }

}
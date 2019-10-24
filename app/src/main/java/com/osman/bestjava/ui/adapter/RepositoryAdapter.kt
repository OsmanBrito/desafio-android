package com.osman.bestjava.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osman.bestjava.R
import com.osman.bestjava.data.entity.Repository
import com.osman.bestjava.ui.view.PullRequestActivity
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter(private val context: Context) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var mRepositories: ArrayList<Repository> = arrayListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(repo: Repository) {
            itemView.repo_tv_name.text = repo.name
            itemView.repo_tv_stars_count.text = repo.stargazersCount.toString()
            itemView.repo_tv_forks_count.text = repo.forksCount.toString()
            itemView.repo_tv_description.text = repo.description
            Glide.with(itemView).load(repo.owner.photo).into(itemView.repo_iv_owner_photo)
            itemView.repo_tv_owner_name.text = repo.owner.login
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
        holder.itemView.card_view.setOnClickListener {
            val i = Intent(holder.itemView.context, PullRequestActivity::class.java)
            i.putExtra("owner", mRepositories[position].owner.login)
            i.putExtra("repo", mRepositories[position].name)
            holder.itemView.context.startActivity(i)
        }
        holder.bindView(mRepositories[position])
    }

}
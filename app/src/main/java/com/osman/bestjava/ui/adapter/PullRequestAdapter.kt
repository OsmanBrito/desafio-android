package com.osman.bestjava.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osman.bestjava.R
import com.osman.bestjava.data.entity.PullRequest
import kotlinx.android.synthetic.main.pull_request_item.view.*

class PullRequestAdapter(private val context: Context) :
    RecyclerView.Adapter<PullRequestAdapter.ViewHolder>() {

    private var mPullRequests: ArrayList<PullRequest> = arrayListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(pllRequest: PullRequest) {
            itemView.repo_tv_name.text = pllRequest.title
            Glide.with(itemView).load(pllRequest.user.photo).into(itemView.repo_iv_owner_photo)
            itemView.repo_tv_owner_name.text = pllRequest.user.login
            itemView.card_view.setOnClickListener {
//                val i = Intent(itemView.context, PullRequestActivity::class.java)
//                i.putExtra("owner", pllRequest.owner.login)
//                i.putExtra("repo", pllRequest.name)
//                itemView.context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pull_request_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPullRequests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mPullRequests[position])
    }

    fun add(pullRequests: ArrayList<PullRequest>) {
        mPullRequests = pullRequests
        notifyDataSetChanged()
    }


}
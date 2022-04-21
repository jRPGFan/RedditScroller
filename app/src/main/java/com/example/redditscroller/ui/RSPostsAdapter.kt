package com.example.redditscroller.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditscroller.R
import com.example.redditscroller.databinding.RedditPostBinding
import com.example.redditscroller.model.RSPost
import kotlinx.android.synthetic.main.reddit_post.view.*

class RSPostsAdapter : PagingDataAdapter<RSPost, RSPostsAdapter.RSViewHolder>(DiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RSViewHolder {
        return RSViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RSViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RSViewHolder(
        parent: ViewGroup,
        private val binding: RedditPostBinding =
            RedditPostBinding.inflate(LayoutInflater.from(parent.context))
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rsPost: RSPost) {
            binding.postText.text = rsPost.title
            binding.upvoteCounter.text = rsPost.upvotes.toString()
            binding.commentsCounter.text = rsPost.commentsAmount.toString()
        }
    }
}
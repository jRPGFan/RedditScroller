package com.example.redditscroller.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.redditscroller.model.RSPost

class DiffItemCallback : DiffUtil.ItemCallback<RSPost>() {
    override fun areItemsTheSame(oldItem: RSPost, newItem: RSPost): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: RSPost, newItem: RSPost): Boolean {
        return oldItem.key == newItem.key && oldItem.title == newItem.title &&
                oldItem.upvotes == newItem.upvotes && oldItem.commentsAmount == newItem.commentsAmount
    }
}
package com.example.redditscroller.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class RSPost(
    @SerializedName("name")
    @PrimaryKey
    val key: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ups")
    val upvotes: Int,
    @SerializedName("num_comments")
    val commentsAmount: Int
)
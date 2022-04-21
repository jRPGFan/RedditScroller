package com.example.redditscroller.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class RSKeys(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val after: String?,
    val before: String?
)
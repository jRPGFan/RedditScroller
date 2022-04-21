package com.example.redditscroller.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditscroller.model.RSPost

@Dao
interface RSPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePosts(post: List<RSPost>)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): PagingSource<Int, RSPost>
}
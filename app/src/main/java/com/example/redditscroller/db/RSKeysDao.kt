package com.example.redditscroller.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditscroller.model.RSKeys

@Dao
interface RSKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKeys(redditKeys: RSKeys)

    @Query("SELECT * FROM keys ORDER BY id DESC")
    suspend fun getKeys(): List<RSKeys>
}
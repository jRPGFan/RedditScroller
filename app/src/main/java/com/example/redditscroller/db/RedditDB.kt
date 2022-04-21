package com.example.redditscroller.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.redditscroller.model.RSKeys
import com.example.redditscroller.model.RSPost

@Database(entities = [RSPost::class, RSKeys::class], version = 1, exportSchema = false)
abstract class RedditDB : RoomDatabase() {
    companion object {
        fun createDB(context: Context) : RedditDB {
            val dbBuilder = Room.databaseBuilder(context, RedditDB::class.java, "redditscroller.db")
            return dbBuilder.build()
        }
    }

    abstract fun redditPostDao(): RSPostDao
    abstract fun redditKeysDao(): RSKeysDao
}
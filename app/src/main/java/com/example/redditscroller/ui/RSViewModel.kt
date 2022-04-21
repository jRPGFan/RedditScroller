package com.example.redditscroller.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.redditscroller.model.RSPost
import com.example.redditscroller.repository.RSRepository
import kotlinx.coroutines.flow.Flow

class RSViewModel(application: Application) : AndroidViewModel(application) {
    private val dataSource = RSRepository(application)
    fun getAawTopPosts(): Flow<PagingData<RSPost>> = dataSource.getPosts().cachedIn(viewModelScope)
}
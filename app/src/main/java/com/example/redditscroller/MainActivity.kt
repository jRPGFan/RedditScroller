package com.example.redditscroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.redditscroller.databinding.ActivityMainBinding
import com.example.redditscroller.ui.RSPostsAdapter
import com.example.redditscroller.ui.RSViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val rsPostsAdapter = RSPostsAdapter()
    private val rsViewModel: RSViewModel by lazy {
        ViewModelProvider(this).get(RSViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.postList.adapter = rsPostsAdapter
        lifecycleScope.launch {
            rsViewModel.getAawTopPosts().collectLatest { pagingData ->
                rsPostsAdapter.submitData(pagingData)
            }
        }
    }

    private fun getAawPosts() {
        lifecycleScope.launch {
            rsViewModel.getAawTopPosts().collectLatest { pagingData ->
                rsPostsAdapter.submitData(pagingData)
            }
        }
    }
}
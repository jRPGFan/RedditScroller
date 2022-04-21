package com.example.redditscroller.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.redditscroller.db.RedditDB
import com.example.redditscroller.model.RSPost
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.flow.Flow

class RSRepository(private val context: Context) {
    private val dataSource = dataSource().create(IDataSource::class.java)
    private val redditDB = RedditDB.createDB(context)

    @OptIn(ExperimentalPagingApi::class)
    fun getPosts(): Flow<PagingData<RSPost>> {
        return if (checkForInternet(context))
            Pager(
                PagingConfig(pageSize = 40, enablePlaceholders = false, prefetchDistance = 3)
            ) { RSPagingSource(dataSource) }.flow
        else
            Pager(
                PagingConfig(pageSize = 40, enablePlaceholders = false, prefetchDistance = 3),
                remoteMediator = RSRemoteMediator(dataSource, redditDB),
                pagingSourceFactory = { redditDB.redditPostDao().getAllPosts() }
            ).flow
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    companion object {
        private const val BASE_URL = "https://www.reddit.com"
        private var retrofit: Retrofit? = null

        fun dataSource(): Retrofit {
            when (retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }
}
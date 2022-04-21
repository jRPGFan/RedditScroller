package com.example.redditscroller.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.redditscroller.model.RSPost
import retrofit2.HttpException
import java.io.IOException

class RSPagingSource(private val dataSource: IDataSource) :
    PagingSource<String, RSPost>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RSPost> {
        return try {
            val response = dataSource.getTopAawPosts(postLimit = params.loadSize)
            val responseData = response.body()?.data
            val posts = responseData?.children?.map { it.data }
            LoadResult.Page(posts ?: listOf(), responseData?.before, responseData?.after)
        }   catch (exception: IOException) { return LoadResult.Error(exception)}
            catch (exception: HttpException) { return LoadResult.Error(exception) }
    }

    override val keyReuseSupported: Boolean = true
    override fun getRefreshKey(state: PagingState<String, RSPost>): String? = null

}
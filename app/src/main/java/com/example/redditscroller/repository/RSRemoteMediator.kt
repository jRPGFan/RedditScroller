package com.example.redditscroller.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.redditscroller.db.RedditDB
import com.example.redditscroller.model.RSKeys
import com.example.redditscroller.model.RSPost
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RSRemoteMediator(private val dataSource: IDataSource, private val redditDB: RedditDB)
    : RemoteMediator<Int, RSPost>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RSPost>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> { state.lastItemOrNull() ?:
                    return MediatorResult.Success(endOfPaginationReached = true)
                    getKeys()
                }
            }
            val response = dataSource.getTopAawPosts(
                postLimit = state.config.pageSize, after = loadKey?.after, before = loadKey?.before)
            val responseData = response.body()?.data
            val posts = responseData?.children?.map { it.data }
            if (posts != null)
                redditDB.withTransaction {
                    redditDB.redditKeysDao().saveKeys(RSKeys(0, responseData.after, responseData.before))
                    redditDB.redditPostDao().savePosts(posts)
                }
            MediatorResult.Success(endOfPaginationReached = responseData?.after == null)
        }   catch (exception: IOException) { MediatorResult.Error(exception) }
            catch (exception: HttpException) { MediatorResult.Error(exception) }
    }

    private suspend fun getKeys(): RSKeys? = redditDB.redditKeysDao().getKeys().firstOrNull()
}
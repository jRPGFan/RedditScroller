package com.example.redditscroller.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {
    @GET("/r/aww/hot.json")
    suspend fun getTopAawPosts(
        @Query("limit") postLimit: Int = 0,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Response<ApiResponse>
}
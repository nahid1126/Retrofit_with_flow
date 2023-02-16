package com.nahid.flow_with_retrofit.network

import com.nahid.flow_with_retrofit.model.GetPostModel
import com.nahid.flow_with_retrofit.model.PostModel
import retrofit2.http.*
import retrofit2.Response

interface ApiService {
    @GET("posts")
    suspend fun getPost(): Response<List<GetPostModel>>

    @POST("posts")
    suspend fun addPost(@Body postModel: PostModel): Response<Boolean>
}
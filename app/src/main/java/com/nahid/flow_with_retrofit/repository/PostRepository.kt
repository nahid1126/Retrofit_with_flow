package com.nahid.flow_with_retrofit.repository

import android.util.Log
import com.nahid.flow_with_retrofit.model.PostModel
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.AppListener
import com.nahid.flow_with_retrofit.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostRepository(private val apiService: ApiService) : AppListener.RequestPost {
    private val postFlow = MutableStateFlow<NetworkResponse<Boolean>>(NetworkResponse.Empty())

    val postResponse = postFlow.asStateFlow()
    override suspend fun requestPost(postModel: PostModel) {

        postFlow.emit(NetworkResponse.Loading())
        try {
            val response = apiService.addPost(postModel)
            if (response.isSuccessful) {
                postFlow.emit(NetworkResponse.Success(true))
                Log.d("TAG", "requestPost success: ")
            } else {
                Log.d("TAG", "requestPost Failed: ")
                postFlow.emit(NetworkResponse.Error("Post Failed!!"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "requestPost FailedC: ")
            postFlow.emit(NetworkResponse.Error(e.message.toString()))
        }
    }


}
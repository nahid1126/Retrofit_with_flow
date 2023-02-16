package com.nahid.flow_with_retrofit.repository

import android.util.Log
import com.nahid.flow_with_retrofit.model.PostModel
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.AppListener
import com.nahid.flow_with_retrofit.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostRepository(private val apiService: ApiService) : AppListener {

    private var postFlow =
        MutableStateFlow<NetworkResponse<List<PostModel>>>(NetworkResponse.Empty())

    override fun post(): StateFlow<NetworkResponse<List<PostModel>>> {
        return postFlow.asStateFlow()
    }

    override suspend fun requestPost() {
        postFlow.emit(NetworkResponse.Loading())

        try {
            val response = apiService.getPost()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    postFlow.emit(NetworkResponse.Success(response.body()!!))
                } else {
                    postFlow.emit(NetworkResponse.Error("Not data Found"))
                }
            } else {
                postFlow.emit(NetworkResponse.Error("Not data Found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            postFlow.emit(NetworkResponse.Error(e.message.toString()))
        }
    }

}
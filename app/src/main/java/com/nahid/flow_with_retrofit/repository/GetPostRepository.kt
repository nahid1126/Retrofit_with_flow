package com.nahid.flow_with_retrofit.repository

import com.nahid.flow_with_retrofit.model.GetPostModel
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.AppListener
import com.nahid.flow_with_retrofit.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetPostRepository(private val apiService: ApiService) : AppListener.GetPost {

    private var getPostFlow =
        MutableStateFlow<NetworkResponse<List<GetPostModel>>>(NetworkResponse.Empty())

    override fun getPost(): StateFlow<NetworkResponse<List<GetPostModel>>> {
        return getPostFlow.asStateFlow()
    }

    override suspend fun requestGetPost() {
        getPostFlow.emit(NetworkResponse.Loading())

        try {
            val response = apiService.getPost()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    getPostFlow.emit(NetworkResponse.Success(response.body()!!))
                } else {
                    getPostFlow.emit(NetworkResponse.Error("Not data Found"))
                }
            } else {
                getPostFlow.emit(NetworkResponse.Error("Not data Found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            getPostFlow.emit(NetworkResponse.Error(e.message.toString()))
        }
    }

}
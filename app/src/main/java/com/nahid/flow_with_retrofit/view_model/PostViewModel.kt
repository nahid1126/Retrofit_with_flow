package com.nahid.flow_with_retrofit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nahid.flow_with_retrofit.network.ApiClient
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.BaseUrl
import com.nahid.flow_with_retrofit.repository.PostRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postRepository: PostRepository

    init {
        val apiService = ApiClient.getApiClient(BaseUrl.baseUrl)!!.create(ApiService::class.java)
        postRepository = PostRepository(apiService)
    }

    val postList = postRepository.post()

    fun requestPostList() {
        viewModelScope.launch {
            withContext(IO) {
                postRepository.requestPost()
            }
        }
    }
}
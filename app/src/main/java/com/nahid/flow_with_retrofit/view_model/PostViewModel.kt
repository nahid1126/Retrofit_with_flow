package com.nahid.flow_with_retrofit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nahid.flow_with_retrofit.model.GetPostModel
import com.nahid.flow_with_retrofit.model.PostModel
import com.nahid.flow_with_retrofit.network.ApiClient
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.BaseUrl
import com.nahid.flow_with_retrofit.repository.PostRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository: PostRepository

    init {
        val apiService = ApiClient.getApiClient(BaseUrl.baseUrl)!!.create(ApiService::class.java)
        postRepository = PostRepository(apiService)
    }

    val postData = postRepository.getPostResponse()

    fun postRequest(postModel: PostModel) {
        viewModelScope.launch {
            withContext(IO) {
                postRepository.requestPost(postModel)
            }
        }
    }
}
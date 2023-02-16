package com.nahid.flow_with_retrofit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nahid.flow_with_retrofit.network.ApiClient
import com.nahid.flow_with_retrofit.network.ApiService
import com.nahid.flow_with_retrofit.network.BaseUrl
import com.nahid.flow_with_retrofit.repository.GetPostRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetPostViewModel(application: Application) : AndroidViewModel(application) {

    private val getPostRepository: GetPostRepository

    init {
        val apiService = ApiClient.getApiClient(BaseUrl.baseUrl)!!.create(ApiService::class.java)
        getPostRepository = GetPostRepository(apiService)
    }

    val postList = getPostRepository.getPost()

    fun requestPostList() {
        viewModelScope.launch {
            withContext(IO) {
                getPostRepository.requestGetPost()
            }
        }
    }
}
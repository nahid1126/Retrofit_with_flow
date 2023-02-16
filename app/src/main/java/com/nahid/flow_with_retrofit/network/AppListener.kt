package com.nahid.flow_with_retrofit.network

import com.nahid.flow_with_retrofit.model.PostModel
import kotlinx.coroutines.flow.StateFlow

interface AppListener {

    fun post(): StateFlow<NetworkResponse<List<PostModel>>>

    suspend fun requestPost()
}
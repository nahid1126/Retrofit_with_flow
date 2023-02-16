package com.nahid.flow_with_retrofit.network

import com.nahid.flow_with_retrofit.model.GetPostModel
import com.nahid.flow_with_retrofit.model.PostModel
import kotlinx.coroutines.flow.StateFlow

interface AppListener {

    interface GetPost {
        fun getPost(): StateFlow<NetworkResponse<List<GetPostModel>>>

        suspend fun requestGetPost()
    }

    interface RequestPost {
       suspend fun requestPost(postModel: PostModel)
    }
}
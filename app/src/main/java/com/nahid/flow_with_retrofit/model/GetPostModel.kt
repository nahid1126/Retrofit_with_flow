package com.nahid.flow_with_retrofit.model

data class GetPostModel(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
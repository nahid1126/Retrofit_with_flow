package com.nahid.flow_with_retrofit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nahid.flow_with_retrofit.databinding.RowItemBinding
import com.nahid.flow_with_retrofit.model.GetPostModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    private var postList = listOf<GetPostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setPostList(postList: List<GetPostModel>) {
        this.postList = ArrayList(postList)
        notifyDataSetChanged()
    }

    class PostHolder(private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(getPostModel: GetPostModel) {
            binding.postValue = getPostModel
        }
    }
}
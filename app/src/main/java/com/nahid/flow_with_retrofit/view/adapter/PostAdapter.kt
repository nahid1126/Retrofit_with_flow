package com.nahid.flow_with_retrofit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nahid.flow_with_retrofit.databinding.RowItemBinding
import com.nahid.flow_with_retrofit.model.PostModel

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    private var postList = listOf<PostModel>()

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

    fun setPostList(postList: List<PostModel>) {
        this.postList = ArrayList(postList)
        notifyDataSetChanged()
    }

    class PostHolder(private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(postModel: PostModel) {
            binding.postValue = postModel
        }
    }
}
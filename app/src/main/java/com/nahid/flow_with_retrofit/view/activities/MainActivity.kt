package com.nahid.flow_with_retrofit.view.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nahid.flow_with_retrofit.databinding.ActivityMainBinding
import com.nahid.flow_with_retrofit.network.NetworkConnect
import com.nahid.flow_with_retrofit.network.NetworkResponse
import com.nahid.flow_with_retrofit.view.adapter.PostAdapter
import com.nahid.flow_with_retrofit.view_model.PostViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var postViewModel: PostViewModel
    private lateinit var postAdapter: PostAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressDialog = ProgressDialog(this).apply {
            setMessage("Please Wait")
            setCancelable(false)
        }

        postAdapter = PostAdapter()
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]

        if (NetworkConnect.isNetworkConnected(this)) {
            postViewModel.requestPostList()
        } else {
            Toast.makeText(this, "Connect Internet", Toast.LENGTH_SHORT).show()
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }

        lifecycleScope.launchWhenCreated {
            postViewModel.postList.collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> {
                        progressDialog.show()
                    }
                    is NetworkResponse.Success -> {
                        it?.let {
                            progressDialog.dismiss()
                            val postList = ArrayList(it.data)
                            with(postAdapter) {
                                setPostList(postList)
                            }
                        }
                    }
                    is NetworkResponse.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

    }
}
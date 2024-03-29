package com.nahid.flow_with_retrofit.view.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.nahid.flow_with_retrofit.databinding.FragmentDialogBinding
import com.nahid.flow_with_retrofit.model.PostModel
import com.nahid.flow_with_retrofit.network.NetworkConnect
import com.nahid.flow_with_retrofit.network.NetworkResponse
import com.nahid.flow_with_retrofit.view_model.PostViewModel
import kotlinx.coroutines.flow.collectLatest

class CustomFragment : DialogFragment() {

    lateinit var binding: FragmentDialogBinding
    lateinit var postViewModel: PostViewModel
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(requireActivity()).apply {
            setMessage("Please Wait")
            setCancelable(false)
        }
        postViewModel = ViewModelProvider(requireActivity())[PostViewModel::class.java]
        dialogSetUp()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            postViewModel.postData.collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> {
                        progressDialog.show()
                    }
                    is NetworkResponse.Success -> {
                        progressDialog.dismiss()
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponse.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
        return binding.root
    }

    private fun dialogSetUp() {
        binding.setOnSubmitClicked {
            if (TextUtils.isEmpty(binding.txtTitle.text.toString())) {
                binding.txtTitle.error = "Enter Title"
            } else if (TextUtils.isEmpty(binding.txtBody.text.toString())) {
                binding.txtBody.error = "Enter body"
            } else {
                if (NetworkConnect.isNetworkConnected(requireActivity())) {
                    postViewModel.postRequest(
                        PostModel(
                            11,
                            101,
                            binding.txtTitle.text.toString(),
                            binding.txtBody.text.toString()
                        )
                    )
                } else {
                    Toast.makeText(requireContext(), "Connect Internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            // Set Match Parent for Full Screen Dialog
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT;
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT;
            it.window?.setLayout(width, height)
        }
    }
}
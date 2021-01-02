package com.vinayak.justcleanassessment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vinayak.justcleanassessment.R
import com.vinayak.justcleanassessment.adapter.CommentsAdapter
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.databinding.FragmentPostBinding
import com.vinayak.justcleanassessment.utils.Status
import com.vinayak.justcleanassessment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostFragment:Fragment(R.layout.fragment_post) {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostBinding.bind(view)
        val commentsAdapter = CommentsAdapter()
        val bundle = this.arguments
        var post: Post? = null
        if (bundle != null) {
             post = bundle.getParcelable("post")!!
        }


        binding.apply {
            recyclerViewComments.apply {
                adapter = commentsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            addFavorite.setOnClickListener {
                post!!.favorite = 1
                mainViewModel.updatePost(post)
            }

            removeFavorite.setOnClickListener {
                post!!.favorite = 0
                mainViewModel.updatePost(post)
            }

        }
        mainViewModel.getComments(post?.id!!)
        mainViewModel.comments.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data.let { res ->
                        commentsAdapter.submitList(res!!)
                        mainViewModel.insertComments(res)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    mainViewModel.getCommentsFromDb(post?.id).observe(viewLifecycleOwner,{
                        commentsAdapter.submitList(it)
                    })
                    Snackbar.make(view, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })



    }

    }
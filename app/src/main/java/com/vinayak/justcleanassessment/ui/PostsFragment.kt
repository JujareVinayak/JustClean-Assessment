package com.vinayak.justcleanassessment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vinayak.justcleanassessment.R
import com.vinayak.justcleanassessment.adapter.PostsAdapter
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.databinding.FragmentPostsBinding
import com.vinayak.justcleanassessment.utils.Status
import com.vinayak.justcleanassessment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment:Fragment(R.layout.fragment_posts), PostsAdapter.OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostsBinding.bind(view)
        val postsAdapter = PostsAdapter(this)

        binding.apply {
            recyclerViewPosts.apply {
                adapter = postsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        }
        mainViewModel.res.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data.let { res ->
                        postsAdapter.submitList(res!!)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                    Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                    // Toast.makeText(this@PostsFragment.requireContext(),it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    override fun onItemClick(post: Post) {
        var bundle = Bundle()
        bundle.putParcelable("post",post)
        findNavController().navigate(R.id.action_postsFragment_to_postFragment,bundle)
    }



}
package com.vinayak.justcleanassessment.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
    private lateinit var  builder: AlertDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostBinding.bind(view)
        builder = setProgressDialog(requireContext(),"Loading...")
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
            addFavorite.visibility = View.GONE
            removeFavorite.visibility = View.GONE
        }
        mainViewModel.getComments(post?.id!!)
        mainViewModel.comments.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                   builder.dismiss()
                    it.data.let { res ->
                        if(res!=null){
                            binding.addFavorite.visibility = View.VISIBLE
                            binding.removeFavorite.visibility = View.VISIBLE
                        }else{
                            binding.addFavorite.visibility = View.GONE
                            binding.removeFavorite.visibility = View.GONE
                        }
                        commentsAdapter.submitList(res!!)
                        mainViewModel.insertComments(res)
                    }
                }
                Status.LOADING -> {
                    binding.addFavorite.visibility = View.GONE
                    binding.removeFavorite.visibility = View.GONE
                   builder.show()
                }
                Status.ERROR -> {
                    builder.dismiss()
                    mainViewModel.getCommentsFromDb(post?.id).observe(viewLifecycleOwner,{
                        if(it.isNotEmpty()){
                            binding.addFavorite.visibility = View.VISIBLE
                            binding.removeFavorite.visibility = View.VISIBLE
                        }else{
                            binding.addFavorite.visibility = View.GONE
                            binding.removeFavorite.visibility = View.GONE
                        }
                        commentsAdapter.submitList(it)
                    })
                    Snackbar.make(view, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })



    }

    private fun setProgressDialog(context: Context, message:String):AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }
    }
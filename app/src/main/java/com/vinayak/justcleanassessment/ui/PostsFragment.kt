package com.vinayak.justcleanassessment.ui

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
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


@Suppress("DEPRECATION")
@AndroidEntryPoint
class PostsFragment:Fragment(R.layout.fragment_posts), PostsAdapter.OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var  builder: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(networkReceiver, intentFilter)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPostsBinding.bind(view)
        val postsAdapter = PostsAdapter(this)
        builder = setProgressDialog(requireContext(), "Loading...")
        binding.apply {
            recyclerViewPosts.apply {
                adapter = postsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        }
        mainViewModel.posts.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    builder.dismiss()
                    it.data.let { res ->
                        postsAdapter.submitList(res!!)
                        mainViewModel.insertPosts(res)
                    }
                }
                Status.LOADING -> {
                    builder.show()
                }
                Status.ERROR -> {
                    builder.dismiss()
                    mainViewModel.getOfflinePosts().observe(viewLifecycleOwner, {
                        postsAdapter.submitList(it)
                    })
                    Snackbar.make(view, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })




    }

    override fun onItemClick(post: Post) {
        var bundle = Bundle()
        bundle.putParcelable("post", post)
        findNavController().navigate(R.id.action_postsFragment_to_postFragment, bundle)
    }

    private fun setProgressDialog(context: Context, message: String):AlertDialog {
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

    //Broadcast Receiver for the internet.
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            when(action){
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    val connManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val netInfos = connManager.allNetworkInfo
                    for (netInfo in netInfos) {
                        when (netInfo.type) {
                            ConnectivityManager.TYPE_MOBILE -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                    mainViewModel.getPosts()
                            }
                            ConnectivityManager.TYPE_MOBILE_HIPRI -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                mainViewModel.getPosts()
                            }
                            ConnectivityManager.TYPE_WIFI -> {
                                if (netInfo.state == NetworkInfo.State.CONNECTED)
                                mainViewModel.getPosts()
                            }

                        }
                    }

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(networkReceiver)
    }
}
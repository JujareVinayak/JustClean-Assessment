package com.vinayak.justcleanassessment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.data.api.MainRepository
import com.vinayak.justcleanassessment.db.PostDAO
import com.vinayak.justcleanassessment.utils.Resource
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository, private val postDAO: PostDAO
): ViewModel(){

    private val _posts = MutableLiveData<Resource<List<Post>>>()

    val posts : LiveData<Resource<List<Post>>>
        get() = _posts

    private val _comments = MutableLiveData<Resource<List<Comment>>>()

    val comments : LiveData<Resource<List<Comment>>>
        get() = _comments

    init {
        getPosts()
    }

    private fun getPosts()  = viewModelScope.launch {
        _posts.postValue(Resource.loading(null))
        try {
            mainRepository.getPosts().let {
                if (it.isSuccessful) {
                    _posts.postValue(Resource.success(it.body()))
                } else {
                    _posts.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }catch (e: UnknownHostException){
            _posts.postValue(Resource.error("No Internet Connection",null))
        }
    }

     fun insertPosts(list: List<Post>) = viewModelScope.launch {
        postDAO.insertPosts(list)
     }

    fun insertComments(list: List<Comment>) = viewModelScope.launch {
        postDAO.insertComments(list)
    }


     fun getComments(postId: Int)  = viewModelScope.launch {
        _comments.postValue(Resource.loading(null))
        try {
            mainRepository.getComments(postId).let {
                if (it.isSuccessful) {
                    _comments.postValue(Resource.success(it.body()))
                } else {
                    _comments.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }catch (e: UnknownHostException){
            _comments.postValue(Resource.error("No Internet Connection",null))
        }
    }

}
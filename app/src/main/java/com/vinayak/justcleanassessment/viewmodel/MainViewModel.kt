package com.vinayak.justcleanassessment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.vinayak.justcleanassessment.data.Post
import com.vinayak.justcleanassessment.data.api.MainRepository
import com.vinayak.justcleanassessment.utils.Resource
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
): ViewModel(){

    private val _res = MutableLiveData<Resource<List<Post>>>()

    val res : LiveData<Resource<List<Post>>>
        get() = _res

    init {
        getPosts()
    }

    private fun getPosts()  = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        try {
            mainRepository.getPosts().let {
                if (it.isSuccessful) {
                    _res.postValue(Resource.success(it.body()))
                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }catch (e: UnknownHostException){
            _res.postValue(Resource.error("No Internet Connection",null))
        }
    }



}
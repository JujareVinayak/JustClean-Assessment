package com.vinayak.justcleanassessment.data.api

import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper:ApiHelper
){
    suspend fun getPosts() = apiHelper.getPosts()
}
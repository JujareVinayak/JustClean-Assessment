package com.vinayak.justcleanassessment.data.api

import com.vinayak.justcleanassessment.data.Post
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getPosts(): Response<Post> = apiService.getPosts()
}
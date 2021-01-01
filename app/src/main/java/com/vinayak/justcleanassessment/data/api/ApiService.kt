package com.vinayak.justcleanassessment.data.api

import com.vinayak.justcleanassessment.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService{

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}
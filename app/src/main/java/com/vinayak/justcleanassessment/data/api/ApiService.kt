package com.vinayak.justcleanassessment.data.api

import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/{post_id}/comments")
    suspend fun getComments(@Path("post_id") postId: Int): Response<List<Comment>>

}
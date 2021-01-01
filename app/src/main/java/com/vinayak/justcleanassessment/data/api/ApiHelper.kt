package com.vinayak.justcleanassessment.data.api

import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post
import retrofit2.Response

interface ApiHelper {
    suspend fun getPosts(): Response<List<Post>>
    suspend fun getComments(postId: Int): Response<List<Comment>>
}
package com.vinayak.justcleanassessment.data.api

import com.vinayak.justcleanassessment.data.Post
import retrofit2.Response

interface ApiHelper {
    suspend fun getPosts(): Response<Post>
}
package com.vinayak.justcleanassessment.db

import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post

class PostRepository(private val dao : PostDAO) {

    val posts = dao.getAllPosts()

    suspend fun insert(post: Post):Long{
        return dao.insertPost(post)
    }

    suspend fun update(post: Post):Int{
        return dao.updatePost(post)
    }

    suspend fun delete(post: Post) : Int{
        return dao.deletePost(post)
    }

    suspend fun deleteAllPosts() : Int{
        return dao.deleteAllPosts()
    }

    val comments = dao.getAllComments()

    suspend fun insertComment(comment: Comment):Long{
        return dao.insertComment(comment)
    }

    suspend fun updateComment(comment: Comment):Int{
        return dao.updateComment(comment)
    }

    suspend fun deleteComment(comment: Comment) : Int{
        return dao.deleteComment(comment)
    }

    suspend fun deleteAllComments() : Int{
        return dao.deleteAllComments()
    }
}
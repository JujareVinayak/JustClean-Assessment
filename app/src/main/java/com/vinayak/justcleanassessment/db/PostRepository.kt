package com.vinayak.justcleanassessment.db

import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post
import javax.inject.Inject

class PostRepository @Inject constructor(private val dao : PostDAO) {

    val posts = dao.getAllPosts()

    suspend fun insertPost(post: Post):Long{
        return dao.insertPost(post)
    }

    suspend fun insertPosts(posts: List<Post>){
        return dao.insertPosts(posts)
    }

    suspend fun updatePost(post: Post):Int{
        return dao.updatePost(post)
    }

    suspend fun deletePost(post: Post) : Int{
        return dao.deletePost(post)
    }

    suspend fun deleteAllPosts() : Int{
        return dao.deleteAllPosts()
    }

    val comments = dao.getAllComments()

    fun commentsByPostId(postId: Int) = dao.getAllCommentsByPostId(postId)

    suspend fun insertComment(comment: Comment):Long{
        return dao.insertComment(comment)
    }
    suspend fun insertComments(posts: List<Comment>){
         dao.insertComments(posts)
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

    val favPosts  = dao.getFavoritePosts()
}
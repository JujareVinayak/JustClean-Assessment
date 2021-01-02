package com.vinayak.justcleanassessment.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post


@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(post: List<Post>)

    @Insert
    suspend fun insertPost(post: Post) : Long

    @Update
    suspend fun updatePost(post: Post) : Int

    @Delete
    suspend fun deletePost(post: Post) : Int

    @Query("DELETE FROM post_data_table")
    suspend fun deleteAllPosts() : Int

    @Query("SELECT * FROM post_data_table")
    fun getAllPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comment: List<Comment>)

    @Insert
    suspend fun insertComment(comment: Comment) : Long

    @Update
    suspend fun updateComment(comment: Comment) : Int

    @Delete
    suspend fun deleteComment(comment: Comment) : Int

    @Query("DELETE FROM comment_data_table")
    suspend fun deleteAllComments() : Int

    @Query("SELECT * FROM comment_data_table")
    fun getAllComments(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_data_table where post_id = :postId")
    fun getAllCommentsByPostId(postId: Int): LiveData<List<Comment>>

    @Query("SELECT * FROM post_data_table where favorite = 1")
    fun getFavoritePosts(): LiveData<List<Post>>
}
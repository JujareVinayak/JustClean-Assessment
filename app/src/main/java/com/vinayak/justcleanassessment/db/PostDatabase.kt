package com.vinayak.justcleanassessment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinayak.justcleanassessment.data.Comment
import com.vinayak.justcleanassessment.data.Post

@Database(entities = [Post::class, Comment::class],version = 1)
abstract class  PostDatabase : RoomDatabase() {

    abstract fun postDAO() : PostDAO
}
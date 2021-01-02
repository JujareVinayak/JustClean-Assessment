package com.vinayak.justcleanassessment.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "post_data_table")
@Parcelize
data class Post( @SerializedName("userId")
                 @Expose
                 @ColumnInfo(name = "user_id") var userId: Int,
                 @PrimaryKey
                 @SerializedName("id")
                 @Expose
                 var id : Int,
                 @SerializedName("title")
                 @Expose
                 var title: String,
                 @SerializedName("body")
                 @Expose
                 var body: String,
                 @Expose(deserialize = false, serialize = false)
                 var favorite: Int
):Parcelable
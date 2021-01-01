package com.vinayak.justcleanassessment.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "comment_data_table")
@Parcelize
data class Comment( @SerializedName("postId")
               @Expose
               @ColumnInfo(name = "post_id") var postId: Int?): Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id : Int? = null


    @SerializedName("name")
    @Expose
     var name: String? = null


    @SerializedName("email")
    @Expose
     var email: String? = null

    @SerializedName("body")
    @Expose
     var body: String? = null
}
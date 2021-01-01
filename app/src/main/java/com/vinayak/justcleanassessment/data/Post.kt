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
                 @ColumnInfo(name = "user_id") var userId: Int?,
                 @PrimaryKey
                 @SerializedName("id")
                 @Expose
                 var id : Int? = null):Parcelable {
    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("body")
    @Expose
    private var body: String? = null

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String?) {
        this.body = body
    }
}
package com.vinayak.justcleanassessment.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Post:Parcelable {
    @SerializedName("userId")
    @Expose
    private var userId: Int? = null

    @SerializedName("id")
    @Expose
    private var _id: Int? = null
    val id : Int?
        get() = _id

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("body")
    @Expose
    private var body: String? = null

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

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
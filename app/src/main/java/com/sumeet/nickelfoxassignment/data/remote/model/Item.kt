package com.sumeet.nickelfoxassignment.data.remote.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("id")
    val id: Id?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("snippet")
    val snippet: Snippet?
)
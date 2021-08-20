package com.sumeet.nickelfoxassignment.data.remote.model


import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("resultsPerPage")
    val resultsPerPage: Int?,
    @SerializedName("totalResults")
    val totalResults: Int?
)
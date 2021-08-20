package com.sumeet.nickelfoxassignment.data.remote.network

import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("search")
    suspend fun getAllResults(
        @Query("part") part : String,
        @Query("key") accessKey : String
    ) : Response<YoutubeResponse>

}
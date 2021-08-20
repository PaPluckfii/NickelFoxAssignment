package com.sumeet.nickelfoxassignment.data.repository

import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import com.sumeet.nickelfoxassignment.util.Resource
import retrofit2.Response

interface MainRepository {

    suspend fun getAllResults(
        part : String,
        maxResult : String,
        pageToken : String?,
        accessToken : String
    ) : Response<YoutubeResponse>

}
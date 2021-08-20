package com.sumeet.nickelfoxassignment.data.repository

import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import com.sumeet.nickelfoxassignment.util.Resource

interface MainRepository {

    suspend fun getAllResults(
        part : String,
        accessToken : String
    ) : Resource<YoutubeResponse>

}
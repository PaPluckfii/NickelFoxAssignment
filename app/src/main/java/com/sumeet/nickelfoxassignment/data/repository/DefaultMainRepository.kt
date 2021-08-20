package com.sumeet.nickelfoxassignment.data.repository

import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import com.sumeet.nickelfoxassignment.data.remote.network.ApiClient
import retrofit2.Response
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val apiClient: ApiClient
) : MainRepository {

    /**
     * Function to get response from api
     */
    override suspend fun getAllResults(
        part: String,
        maxResult: String,
        pageToken: String?,
        accessToken: String
    ): Response<YoutubeResponse> {
        return apiClient.getAllResults(part, maxResult, pageToken, accessToken)
    }

}
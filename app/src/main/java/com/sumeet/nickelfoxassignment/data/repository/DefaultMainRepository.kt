package com.sumeet.nickelfoxassignment.data.repository

import android.util.Log
import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import com.sumeet.nickelfoxassignment.data.remote.network.ApiClient
import com.sumeet.nickelfoxassignment.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val apiClient: ApiClient
) : MainRepository {

    override suspend fun getAllResults(
        part : String,
        accessToken: String
    ): Resource<YoutubeResponse> {
        return try {
            val response = apiClient.getAllResults(part,accessToken)
            val result = response.body()
            if(response.isSuccessful) {
                Log.d("responseFormApi", "result found")
                Resource.Success(result)
            }
            else {
                Log.d("responseFormApi", response.errorBody().toString())
                Resource.Error(response.message())
            }
        }catch (e : Exception){
            Log.d("responseFormApi",e.message.toString())
            Resource.Error(e.message?:"Some error occurred")
        }
    }

}
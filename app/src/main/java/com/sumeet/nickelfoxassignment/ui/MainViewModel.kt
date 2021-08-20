package com.sumeet.nickelfoxassignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeet.nickelfoxassignment.data.remote.model.YoutubeResponse
import com.sumeet.nickelfoxassignment.data.repository.MainRepository
import com.sumeet.nickelfoxassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepository
) : ViewModel(){

    private val accessToken = "AIzaSyCV6qWuaWijGB2OfsNL3hN5zlIcTNRKzGA"
    private val _videoList : MutableLiveData<Resource<YoutubeResponse>> = MutableLiveData()
    val videoList : LiveData<Resource<YoutubeResponse>> = _videoList
    var videoListResponse : YoutubeResponse? = null
    var currentPage : String? = null

    fun getAllResults() {
        viewModelScope.launch(Dispatchers.IO) {
            _videoList.postValue(Resource.Loading())
            val response = repository.getAllResults(
                "snippet",
                "20",
                currentPage,
                accessToken
            )
            _videoList.postValue(handleApiResponse(response))
        }
    }

    private fun handleApiResponse(response : Response<YoutubeResponse>) : Resource<YoutubeResponse>{
        if(response.isSuccessful && response.body() != null){
            currentPage = response.body()!!.nextPageToken ?: "no pages left"
            if(videoListResponse == null){
                videoListResponse = response.body()
            }else{
                val oldList = videoListResponse?.items
                val newList = response.body()?.items
                oldList?.addAll(newList!!)
            }
            return Resource.Success(videoListResponse ?: response.body())
        }
        return Resource.Error(response.message())
    }

}
package com.sumeet.nickelfoxassignment.ui

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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepository
) : ViewModel(){

    sealed class ResultsEvent{
        class Success(val result : YoutubeResponse) : ResultsEvent()
        class Failure(val error : String) : ResultsEvent()
        object Loading : ResultsEvent()
        object Empty : ResultsEvent()
    }

    private val _result = MutableStateFlow<ResultsEvent>(ResultsEvent.Empty)
    private val accessToken = "AIzaSyCV6qWuaWijGB2OfsNL3hN5zlIcTNRKzGA"
    val result : StateFlow<ResultsEvent> = _result

    fun getAllResults() {
        viewModelScope.launch(Dispatchers.IO) {
            _result.value = ResultsEvent.Loading
            when (val response = repository.getAllResults("snippet", accessToken)) {
                is Resource.Error -> _result.value = ResultsEvent.Failure(response.message!!)
                is Resource.Success -> _result.value = ResultsEvent.Success(response.data!!)
            }
        }
    }
}
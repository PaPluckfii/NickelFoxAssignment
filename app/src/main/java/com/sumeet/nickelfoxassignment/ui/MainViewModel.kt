package com.sumeet.nickelfoxassignment.ui

import androidx.lifecycle.ViewModel
import com.sumeet.nickelfoxassignment.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepository
) : ViewModel(){

}
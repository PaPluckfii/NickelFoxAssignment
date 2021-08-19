package com.sumeet.nickelfoxassignment.data.repository

import com.sumeet.nickelfoxassignment.data.remote.network.ApiClient
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val apiClient: ApiClient
) : MainRepository {
}
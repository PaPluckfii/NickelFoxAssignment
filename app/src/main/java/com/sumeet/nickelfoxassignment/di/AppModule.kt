package com.sumeet.nickelfoxassignment.di

import com.sumeet.nickelfoxassignment.data.remote.network.ApiClient
import com.sumeet.nickelfoxassignment.data.repository.DefaultMainRepository
import com.sumeet.nickelfoxassignment.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideYoutubeApi() : ApiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(apiClient: ApiClient) : MainRepository = DefaultMainRepository(apiClient)

}
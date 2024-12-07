package com.android.assignment.network

import com.android.assignment.BuildConfig
import com.android.assignment.models.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("3/trending/movie/week?language=en-US&api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieList():Response<MovieList>

}
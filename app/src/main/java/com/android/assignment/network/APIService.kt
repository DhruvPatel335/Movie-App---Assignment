package com.android.assignment.network

import com.android.assignment.models.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("3/trending/movie/week?language=en-US&api_key=5d9a3e183817fb9065c86ee0dfea012c")
    suspend fun getMovieList():Response<MovieList>

}
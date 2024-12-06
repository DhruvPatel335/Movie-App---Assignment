package com.android.assignment.repository

import com.android.assignment.models.Movie
import com.android.assignment.models.MovieList
import com.android.assignment.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieRepository {
    private val apiService = ApiClient.apiService
    private val movieList = MutableStateFlow(MovieList())
    val getMovies: StateFlow<MovieList>
        get() = movieList

    suspend fun getMovies() {
        val response = apiService.getMovieList()
        if (response.isSuccessful && response.body() != null) {
            movieList.emit(response.body()!!)
        }
    }
}
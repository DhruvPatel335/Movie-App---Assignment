package com.android.assignment.repository

import com.android.assignment.models.MovieList
import com.android.assignment.network.ApiClient
import com.android.assignment.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieRepository {
    private val apiService = ApiClient.apiService
    private val movieList = MutableStateFlow<NetworkResult<MovieList>>(NetworkResult.Loading())
    val getMovies: StateFlow<NetworkResult<MovieList>>
        get() = movieList

    suspend fun getMovies() {
        movieList.emit(NetworkResult.Loading())
        try {
            val response = apiService.getMovieList()
            if (response.isSuccessful && response.body() != null) {
                movieList.emit(NetworkResult.Success(response.body()!!))
            } else {
                movieList.emit(NetworkResult.Failure("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            movieList.emit(NetworkResult.Failure("Error: ${e.localizedMessage}"))
        }
    }
}
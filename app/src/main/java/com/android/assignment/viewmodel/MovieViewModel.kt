package com.android.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.assignment.models.MovieList
import com.android.assignment.repository.MovieRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val movieList: StateFlow<MovieList>
        get() = movieRepository.getMovies

    init {
        viewModelScope.launch {
            movieRepository.getMovies()
        }
    }
}
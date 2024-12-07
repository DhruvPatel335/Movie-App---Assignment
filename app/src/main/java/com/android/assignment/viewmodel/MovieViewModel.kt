package com.android.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.assignment.models.Movie
import com.android.assignment.models.MovieList
import com.android.assignment.network.NetworkResult
import com.android.assignment.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private val movieList: StateFlow<NetworkResult<MovieList>>
        get() = movieRepository.getMovies
    val movies: StateFlow<NetworkResult<MovieList>>
        get() = movieList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredMovies: StateFlow<List<Movie>> = combine(
        movieList,
        _searchQuery
    ) { result, query ->
        if (result is NetworkResult.Success && query.isEmpty()) {
            result.data?.results ?: emptyList()
        } else if (result is NetworkResult.Success) {
            result.data?.results?.filter { it.title.contains(query, ignoreCase = true) }
                ?: emptyList()
        } else {
            emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    init {
        viewModelScope.launch {
            movieRepository.getMovies()
        }
    }
}
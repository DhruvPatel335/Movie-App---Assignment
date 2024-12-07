package com.android.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.assignment.models.Movie
import com.android.assignment.models.MovieList
import com.android.assignment.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private val movieList: StateFlow<MovieList>
        get() = movieRepository.getMovies

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredMovies: StateFlow<List<Movie>> = combine(
        movieList,
        _searchQuery
    ) { movieList, query ->
        if (query.isEmpty()) {
            movieList.results
        } else {
            movieList.results.filter { it.title.contains(query, ignoreCase = true) }
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
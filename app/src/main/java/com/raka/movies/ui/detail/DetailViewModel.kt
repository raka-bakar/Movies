package com.raka.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.domain.usecase.BookmarkUseCase
import com.raka.movies.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val dispatcherIo: CoroutineDispatcher,
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val movie = MutableStateFlow<CallResult<MovieItemCompact>>(CallResult.Initial())
    val _movie: StateFlow<CallResult<MovieItemCompact>>
        get() = movie

    fun getMovie(movieId: Int) {
        viewModelScope.launch(dispatcherIo) {
            moviesUseCase.getMovie(movieId).collect { result ->
                movie.value = result
            }
        }
    }

    fun onBookmarkClicked(movie: MovieItemCompact) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.isBookmarked = !movie.isBookmarked
            if (movie.isBookmarked) {
                bookmarkUseCase.bookmarkMovie(movie = movie)
            } else {
                bookmarkUseCase.unBookmarkMovie(movie)
            }
            getMovie(movieId = movie.id)
        }
    }
}
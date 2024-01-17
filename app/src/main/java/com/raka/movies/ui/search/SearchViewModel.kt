package com.raka.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.movies.data.CallResult
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.domain.usecase.BookmarkUseCase
import com.raka.movies.domain.usecase.MoviesUseCase
import com.raka.movies.utils.RefreshFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val dispatcherIo: CoroutineDispatcher,
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {
    val movieList =
        RefreshFlow {
            moviesUseCase.getMoviesList()
                .stateIn(
                    viewModelScope + dispatcherIo,
                    SharingStarted.Eagerly,
                    CallResult.Initial()
                )
        }

    fun addBookmark(movie: MovieItemCompact) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.isBookmarked = !movie.isBookmarked
            if (movie.isBookmarked) {
                bookmarkUseCase.bookmarkMovie(movie = movie)
            } else {
                bookmarkUseCase.unBookmarkMovie(movie)
            }
        }
    }
}
package com.raka.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.data.CallResult
import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCase
import com.raka.movies.domain.usecase.movies.GetMoviesListUseCase
import com.raka.movies.model.MovieItemCompact
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
    private val dispatcherIo: CoroutineDispatcher,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {
    val movieList =
        RefreshFlow {
            getMoviesListUseCase.getMoviesList()
                .stateIn(
                    viewModelScope + dispatcherIo,
                    SharingStarted.Eagerly,
                    CallResult.Initial()
                )
        }

    fun onBookmarkClicked(movie: MovieItemCompact) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.isBookmarked = !movie.isBookmarked
            if (movie.isBookmarked) {
                bookmarkMovieUseCase.bookmarkMovie(movie = movie)
            } else {
                unbookmarkMovieUseCase.unBookmarkMovie(movie)
            }
        }
    }
}
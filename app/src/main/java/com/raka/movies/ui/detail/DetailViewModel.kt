package com.raka.movies.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movies.data.CallResult
import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCase
import com.raka.movies.domain.usecase.movies.GetMovieUseCase
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.navigation.MainNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dispatcherIo: CoroutineDispatcher,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = MainNavigation.Detail.Arguments(savedStateHandle)

    val idMovie = args.idMovie.get()

    var movie = MutableStateFlow<CallResult<MovieItemCompact>>(CallResult.Initial())
        private set

    fun getMovie(movieId: Int) {
        viewModelScope.launch(dispatcherIo) {
            getMovieUseCase.getMovie(idMovie).collect { result ->
                movie.value = result
            }
        }
    }

    fun onBookmarkClicked(movie: MovieItemCompact) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.isBookmarked = !movie.isBookmarked
            if (movie.isBookmarked) {
                bookmarkMovieUseCase.bookmarkMovie(movie = movie)
            } else {
                unbookmarkMovieUseCase.unBookmarkMovie(movie)
            }
            getMovie(movieId = movie.id)
        }
    }
}
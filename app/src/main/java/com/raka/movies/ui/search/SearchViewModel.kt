package com.raka.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raka.movies.domain.usecase.bookmark.BookmarkMovieUseCase
import com.raka.movies.domain.usecase.bookmark.UnbookmarkMovieUseCase
import com.raka.movies.domain.usecase.movies.GetMoviesListUseCase
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.utils.RefreshFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherIo: CoroutineDispatcher,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
    private val getMoviesListUseCase: GetMoviesListUseCase
) : ViewModel() {
    // Check whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    var isSearching = _isSearching.asStateFlow()

    // Check the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    val movieList =
        RefreshFlow {
            _searchText.debounce(300.toDuration(DurationUnit.MILLISECONDS)).flatMapLatest {
                getMoviesListUseCase.getMoviesList(searchText = it)
            }.flowOn(dispatcherIo)
        }

    fun onBookmarkClicked(movie: MovieItemCompact) {
        viewModelScope.launch(Dispatchers.IO) {
            movie.isBookmarked = !movie.isBookmarked
            if (movie.isBookmarked) {
                bookmarkMovieUseCase.bookmarkMovie(movie = movie)
            } else {
                unbookmarkMovieUseCase.unBookmarkMovie(movie)
            }
            movieList.refresh()
        }
    }
}
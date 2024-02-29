package com.raka.movies.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.movies.data.CallResult
import com.raka.movies.ui.detail.DetailScreen
import com.raka.movies.ui.detail.DetailViewModel
import com.raka.movies.ui.home.HomeScreen
import com.raka.movies.ui.home.HomeViewModel
import com.raka.movies.ui.navigation.MainNavigation.Detail.navigateTo
import com.raka.movies.ui.navigation.MainNavigation.Search.navigateToSearch
import com.raka.movies.ui.search.SearchScreen
import com.raka.movies.ui.search.SearchViewModel

/**
 * MainNavigation graph that contains first app page
 */
sealed class MainNavigation(override val route: String) : Navigation(route) {
    override val graphId = "Root"

    companion object {
        /**
         * get all destinations on this graph
         */
        fun getAllNavigation() = setOf(Home, Detail, Search)
    }

    /**
     * MainScreen
     */
    object Home : MainNavigation("HOME_SCREEN") {
        context(NavGraphBuilder)
        override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: HomeViewModel = hiltViewModel()
                val favoCallResult by viewModel.favouriteMoviesList
                    .data.collectAsStateWithLifecycle(
                        initialValue = CallResult.Initial()
                    )
                val staffCallResult by viewModel.staffPickList.data.collectAsStateWithLifecycle(
                    initialValue = CallResult.Initial()
                )
                HomeScreen(
                    callResultFavorite = favoCallResult,
                    callResultStaff = staffCallResult,
                    onBookmarkClicked = viewModel::onBookmarkClicked,
                    toSearchScreen = { controller.navigateToSearch() }
                ) {
                    controller.navigateTo(idMovie = it)
                }
            }
        }
    }

    object Detail : MainNavigation("DETAIL_SCREEN") {
        object ArgKeys {
            const val ID_MOVIE = "ID_MOVIE"
        }

        class Arguments(savedStateHandle: SavedStateHandle) {
            val idMovie = SavedArgument(savedStateHandle, ArgKeys.ID_MOVIE, 0)
        }

        fun NavController.navigateTo(idMovie: Int) {
            navigate(route = "$route/$idMovie")
        }

        override fun getArguments() = listOf(
            navArgument(ArgKeys.ID_MOVIE) {
                type = NavType.IntType
                defaultValue = 0
            }
        )

        context(NavGraphBuilder)
        override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: DetailViewModel = hiltViewModel()
                val idMovie = viewModel.idMovie
                DetailScreen(idMovie)
            }
        }
    }

    object Search : MainNavigation("SEARCH_SCREEN") {

        fun NavController.navigateToSearch() {
            navigate(route)
        }

        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: SearchViewModel = hiltViewModel()
                val searchText by viewModel.searchText.collectAsStateWithLifecycle()
                val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
                val callResult by viewModel.movieList.data.collectAsStateWithLifecycle(
                    initialValue = CallResult.Initial()
                )
                SearchScreen(
                    callResult = callResult,
                    searchText = searchText,
                    onSearchTextChange = viewModel::onSearchTextChange,
                    isSearching = isSearching,
                    onToggleSearch = viewModel::onToggleSearch,
                    onBackPressed = { controller.popBackStack() }
                )
            }
        }
    }
}
package com.raka.movies.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.movies.data.CallResult
import com.raka.movies.ui.detail.DetailScreen
import com.raka.movies.ui.detail.DetailViewModel
import com.raka.movies.ui.home.HomeScreen
import com.raka.movies.ui.home.HomeViewModel
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
        fun getAllNavigation() = setOf(Home)
    }

    /**
     * MainScreen
     */
    object Home : MainNavigation("HOME_SCREEN") {
        context(NavGraphBuilder)
        override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: HomeViewModel = hiltViewModel()
                val callResult by viewModel.favouriteMoviesList.data.collectAsStateWithLifecycle(
                    initialValue = CallResult.Initial()
                )
                HomeScreen(callResult = callResult)
            }
        }
    }

    object Detail : MainNavigation("DETAIL_SCREEN") {
        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: DetailViewModel = hiltViewModel()
                DetailScreen()
            }
        }
    }

    object Search : MainNavigation("SEARCH_SCREEN") {
        context(NavGraphBuilder) override fun compose(controller: NavController) {
            composable(route = getFullRoute(), arguments = getArguments()) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen()
            }
        }
    }
}
package com.raka.movies

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movies.data.CallResult
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.RootActivity
import com.raka.movies.ui.search.SearchScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    val callResult = CallResult.Success(
        listOf(
            MovieItemCompact(
                id = 0,
                title = "Movie 1",
                genres = listOf(),
                posterUrl = "https://image.tmdb.org/t/p/w500/iZf0KyrE25z1sage4SYFLCCrMi9.jpg"
            ),
            MovieItemCompact(
                id = 1,
                title = "Movie 2",
                genres = listOf(),
                posterUrl = "https://image.tmdb.org/t/p/w500/iZf0KyrE25z1sage4SYFLCCrMi9.jpg"
            )
        )
    )

    @Test
    fun testAllComponentsInSearchScreen() {
        composeRule.activity.setContent {
            SearchScreen(
                callResult = callResult,
                searchText = "",
                onSearchTextChange = {},
                isSearching = true,
                onToggleSearch = { /*TODO*/ },
                onBackPressed = { /*TODO*/ },
                toDetailScreen = {},
                onBookmarkClicked = {}
            )
            composeRule.waitForIdle()
            composeRule.onNodeWithTag("topAppBar").assertIsDisplayed()
            composeRule.onNodeWithTag("searchBar").assertIsDisplayed()
        }
    }

    @Test
    fun testSearchBar() {
        composeRule.activity.setContent {
            SearchScreen(
                callResult = CallResult.Initial(),
                searchText = "",
                onSearchTextChange = {},
                isSearching = true,
                onToggleSearch = { /*TODO*/ },
                onBackPressed = { /*TODO*/ },
                toDetailScreen = {},
                onBookmarkClicked = {}
            )

            composeRule.waitForIdle()
            composeRule.onNodeWithTag("searchBar").performTextInput("Mov")
            composeRule.waitForIdle()
            composeRule.onNodeWithTag("searchBar").onChildAt(1).assertIsDisplayed()
        }
    }
}
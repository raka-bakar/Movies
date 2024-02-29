package com.raka.movies.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.home.LazyColumnStaff
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    callResult: CallResult<List<MovieItemCompact>>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isSearching: Boolean,
    onToggleSearch: () -> Unit,
    onBackPressed: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.all_movies),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.image_desc),
                        tint = Color.Unspecified
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.testTag("searchTopAppBar")
        )
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                query = searchText,
                onQueryChange = onSearchTextChange,
                onSearch = onSearchTextChange,
                active = isSearching,
                onActiveChange = { onToggleSearch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                when (callResult) {
                    is CallResult.Success -> {
                        callResult.data?.let { data ->
                            LazyColumnStaff(
                                data = data,
                                onBookmarkClicked = {},
                                onPosterClick = {}
                            )
                        }
                    }

                    is CallResult.Error -> {
                        Timber.e(callResult.message)
                        Text(text = "No Movie Found")
                    }

                    else -> {
                        Text(text = "No Movie Found")
                    }
                }
            }
        }
    }
}
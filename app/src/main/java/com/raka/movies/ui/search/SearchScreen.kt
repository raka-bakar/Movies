package com.raka.movies.ui.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun SearchScreen() {
    Text(text = "Search Screen", modifier = Modifier.testTag("searchTitle"))
}
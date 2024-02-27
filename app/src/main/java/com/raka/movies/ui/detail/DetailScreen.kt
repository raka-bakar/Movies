package com.raka.movies.ui.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun DetailScreen(idMovie: Int) {
    Text(text = "Detail Screen $idMovie", modifier = Modifier.testTag("detailTitle"))
}
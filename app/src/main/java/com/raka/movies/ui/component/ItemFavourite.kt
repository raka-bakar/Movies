package com.raka.movies.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemFavourite(item: MovieItemCompact, modifier: Modifier, onClick: (Int) -> Unit) {
    Card(
        onClick = { onClick(item.id) },
        modifier = Modifier
            .then(modifier)
            .width(dimensionResource(id = R.dimen.item_favorite_width))
            .height(dimensionResource(id = R.dimen.item_favorite_height))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rating_detail_margin)))
    ) {
        AsyncImage(
            model = item.posterUrl,
            contentDescription = null,
        )
    }
}
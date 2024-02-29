package com.raka.movies.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact

@Composable
fun ItemStaff(
    item: MovieItemCompact,
    onBookmarkClicked: (MovieItemCompact) -> Unit,
    onPosterClick: (Int) -> Unit,
    tag: Int
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        val (posterRef, yearRef, titleRef) = createRefs()
        val (ratingRef, buttonRef) = createRefs()

        AsyncImage(
            model = item.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .constrainAs(posterRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .width(dimensionResource(id = R.dimen.poster_width))
                .height(dimensionResource(id = R.dimen.poster_height))
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.btn_corner_radius)))
                .clickable { onPosterClick(item.id) }
                .testTag("image$tag")
        )

        Text(
            text = item.releaseDate ?: "",
            color = Color.Gray,
            modifier = Modifier
                .constrainAs(yearRef) {
                    top.linkTo(parent.top)
                    start.linkTo(posterRef.end)
                }
                .padding(
                    top = dimensionResource(id = R.dimen.rv_favorite_margin),
                    start = dimensionResource(id = R.dimen.text_year_margin_start)
                )
        )
        Text(
            text = item.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(yearRef.bottom)
                    start.linkTo(posterRef.end)
                    end.linkTo(buttonRef.start)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    start = dimensionResource(id = R.dimen.text_year_margin_start)
                )
        )
//        RatingStar(
//            onStarClick = {},
//            maxRating = 5,
//            rating = item.rating ?: 0f,
//            isIndicator = true,
//            modifier = Modifier
//                .constrainAs(ratingRef) {
//                    top.linkTo(titleRef.bottom)
//                    start.linkTo(posterRef.end)
//                }
//                .padding(
//                    start = dimensionResource(id = R.dimen.text_year_margin_start)
//                )
//        )
        val imgResource: Painter = if (item.isBookmarked) {
            painterResource(id = R.drawable.ic_favorite_filled)
        } else {
            painterResource(id = R.drawable.ic_favorite_unfilled)
        }
        IconButton(
            onClick = { onBookmarkClicked(item) },
            modifier = Modifier
                .constrainAs(buttonRef) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .padding(top = 18.dp)
        ) {
            Icon(
                painter = imgResource,
                contentDescription = stringResource(id = R.string.image_desc),
                tint = Color.Unspecified
            )
        }
    }
}
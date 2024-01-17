package com.raka.movies.ui.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.raka.movies.R
import com.raka.movies.data.CallResult
import com.raka.movies.data.model.MovieItemCompact
import com.raka.movies.ui.components.RatingBar

@Composable
fun DetailScreen(
    result: CallResult<MovieItemCompact>,
    onCloseClicked: () -> Unit,
    viewModel: DetailViewModel
) {
    if (result is CallResult.Success && result.data != null) {
        var bookmarkState by remember {
            mutableStateOf(result.data.isBookmarked)
        }
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (btnBookmark, btnClose, imagePoster) = createRefs()
            val (rating, date, duration) = createRefs()
            IconButton(
                modifier = Modifier
                    .constrainAs(btnClose) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(top = dimensionResource(id = R.dimen.poster_margin_bottom)),
                onClick = onCloseClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(
                        id = R.string.image_desc
                    ),
                    tint = Color.Unspecified
                )
            }

            IconButton(
                modifier = Modifier
                    .constrainAs(btnBookmark) {
                        top.linkTo(parent.top)
                        end.linkTo(btnClose.start)
                    }
                    .padding(top = dimensionResource(id = R.dimen.poster_margin_bottom)),
                onClick = {
                    bookmarkState = !bookmarkState
                    viewModel.addBookmark(result.data)
                }
            ) {
                if (bookmarkState) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite_filled),
                        contentDescription = stringResource(
                            id = R.string.image_desc
                        ),
                        tint = Color.Unspecified
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite_unfilled),
                        contentDescription = stringResource(
                            id = R.string.image_desc
                        ),
                        tint = Color.Unspecified
                    )
                }
            }

            AsyncImage(
                model = result.data.posterUrl,
                contentDescription = stringResource(id = R.string.image_desc),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.poster_detail_margin))
                    .width(dimensionResource(id = R.dimen.poster_detail_width))
                    .height(dimensionResource(id = R.dimen.poster_detail_height))
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.btn_corner_radius)))
                    .constrainAs(imagePoster) {
                        top.linkTo(btnBookmark.bottom)
                        centerHorizontallyTo(parent)
                    }
            )
            RatingBar(rating = result.data.rating ?: 0f, modifier = Modifier
                .constrainAs(rating) {
                    top.linkTo(imagePoster.bottom)
                    centerHorizontallyTo(parent)
                }
                .padding(top = dimensionResource(id = R.dimen.rating_detail_margin)))
        }
    } else if (result is CallResult.Error) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = result.message ?: stringResource(id = R.string.error_message))
        }
    }
}
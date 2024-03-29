package com.raka.movies.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.ui.component.ItemFavourite
import com.raka.movies.ui.component.ItemStaff
import com.raka.movies.utils.Utils
import timber.log.Timber

const val waveEmoji = 0x1F44B
const val ROUNDED_SHAPE_SIZE = 50

@Composable
fun HomeScreen(
    callResultFavorite: CallResult<List<MovieItemCompact>>,
    callResultStaff: CallResult<List<MovieItemCompact>>,
    onBookmarkClicked: (MovieItemCompact) -> Unit,
    toSearchScreen: () -> Unit,
    toDetailScreen: (Int) -> Unit
) {
    val greeting = stringResource(R.string.greetings) + Utils.getEmojiByUnicode(waveEmoji)

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.page_margin_start_end)
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_boy_male),
                contentDescription = stringResource(id = R.string.image_desc),
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.page_margin_top)
                    )
                    .height(dimensionResource(id = R.dimen.icon_size_medium))
                    .width(dimensionResource(id = R.dimen.icon_size_medium))
                    .testTag("imageProfile")

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = AnnotatedString(greeting),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_size_small),
                            top = dimensionResource(id = R.dimen.page_margin_top)
                        )
                        .testTag("greeting")
                )
                Text(
                    text = stringResource(id = R.string.default_name),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_size_small)
                        )
                        .testTag("username")
                )
            }
            OutlinedButton(
                onClick = toSearchScreen,
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.margin_size_small),
                        top = dimensionResource(id = R.dimen.page_margin_top)
                    )
                    .size(dimensionResource(id = R.dimen.icon_size_medium))
                    .testTag("searchButton"),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.white))

            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.image_desc)
                )
            }
        }

        when (callResultFavorite) {
            is CallResult.Success -> {
                callResultFavorite.data?.let { data ->
                    FavouriteTitle()
                    LazyRowFavourite(modifier = Modifier, data, toDetailScreen)
                }
            }

            is CallResult.Error -> {
                Timber.e(callResultFavorite.message)
            }

            else -> {
                // CallResult.initial , do nothing
            }
        }

        when (callResultStaff) {
            is CallResult.Success -> {
                callResultStaff.data?.let { data ->
                    StaffPickTitle()
                    LazyColumnStaff(data = data, onBookmarkClicked, onPosterClick = toDetailScreen)
                }
            }

            is CallResult.Error -> {
                Timber.e(callResultFavorite.message)
            }

            else -> {
                // CallResult.initial , do nothing
            }
        }
    }
}

@Composable
fun FavouriteTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.page_margin_start_end),
                top = dimensionResource(id = R.dimen.btn_favorite_margin)
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(id = R.string.your),
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = stringResource(id = R.string.favourite),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Composable
fun LazyRowFavourite(
    modifier: Modifier,
    data: List<MovieItemCompact> = emptyList(),
    onClick: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.page_margin_start_end),
                top = dimensionResource(id = R.dimen.rv_favorite_margin)
            )
            .testTag("favoriteList"),

        ) {
        itemsIndexed(data) { index, item ->
            if (index == 0) {
                ItemFavourite(item = item, modifier = Modifier, onClick)
            } else {
                val modifierInner = Modifier
                    .padding(start = dimensionResource(id = R.dimen.margin_size_small))
                ItemFavourite(item = item, modifier = modifierInner, onClick)
            }
        }

        item(1) {
            Column(
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.item_favorite_width))
                    .height(dimensionResource(id = R.dimen.item_favorite_height)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    border = BorderStroke(1.dp, Color.Blue),
                    shape = RoundedCornerShape(ROUNDED_SHAPE_SIZE)
                ) {
                    Text(text = stringResource(id = R.string.see_all))
                }
            }
        }
    }
}

@Composable
fun StaffPickTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.page_margin_start_end),
                top = dimensionResource(id = R.dimen.btn_favorite_margin)
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(id = R.string.our),
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = stringResource(id = R.string.staff_pick),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Composable
fun LazyColumnStaff(
    data: List<MovieItemCompact> = emptyList(),
    onBookmarkClicked: (MovieItemCompact) -> Unit,
    onPosterClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(id = R.dimen.page_margin_start_end),
                top = dimensionResource(id = R.dimen.rv_favorite_margin),
                end = dimensionResource(id = R.dimen.page_margin_start_end)
            )
            .testTag("staffList")
    ) {
        items(
            count = data.size,
            key = { data[it].id },
            itemContent = {
                ItemStaff(
                    item = data[it],
                    onBookmarkClicked = onBookmarkClicked,
                    onPosterClick = onPosterClick,
                    tag = it
                )
            }
        )
    }
}
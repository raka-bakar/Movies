package com.raka.movies.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.movies.data.CallResult
import com.raka.movies.R
import com.raka.movies.model.MovieItemCompact
import com.raka.movies.utils.Utils
import timber.log.Timber

const val waveEmoji = 0x1F44B

@Composable
fun HomeScreen(callResult: CallResult<List<MovieItemCompact>>) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (imageProfileRef, greetingRef, usernameRef) = createRefs()
        val (searchButtonRef) = createRefs()
        val greeting = stringResource(R.string.greetings) + Utils.getEmojiByUnicode(waveEmoji)
        Image(
            painter = painterResource(id = R.drawable.avatar_boy_male),
            contentDescription = stringResource(id = R.string.image_desc),
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.page_margin_start_end),
                    top = dimensionResource(id = R.dimen.page_margin_top)
                )
                .constrainAs(imageProfileRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .height(dimensionResource(id = R.dimen.icon_size_medium))
                .width(dimensionResource(id = R.dimen.icon_size_medium))

        )
        Text(
            text = AnnotatedString(greeting),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(greetingRef) {
                    top.linkTo(parent.top)
                    start.linkTo(imageProfileRef.end)
                }
                .padding(
                    start = dimensionResource(id = R.dimen.margin_size_small),
                    top = dimensionResource(id = R.dimen.page_margin_top)
                )
        )
        Text(
            text = stringResource(id = R.string.default_name),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .constrainAs(usernameRef) {
                    top.linkTo(greetingRef.bottom)
                    start.linkTo(imageProfileRef.end)
                }
                .padding(
                    start = dimensionResource(id = R.dimen.margin_size_small)
                )
        )

        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .constrainAs(searchButtonRef) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(
                    end = dimensionResource(id = R.dimen.margin_size_small),
                    top = dimensionResource(id = R.dimen.page_margin_top)
                )
                .size(dimensionResource(id = R.dimen.icon_size_medium)),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(colorResource(id = R.color.white))

        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(id = R.string.image_desc)
            )
        }
        when (callResult) {
            is CallResult.Success -> {
                callResult.data?.let { data ->

                }
            }

            is CallResult.Error -> {
                Timber.e(callResult.message)

            }

            else -> {
                // CallResult.initial , do nothing
            }
        }

    }
}
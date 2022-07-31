package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.GenreName
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.glide.GlideImage

const val MOVIE_CARD_TEST_TAG = "MOVIE_CARD_TEST_TAG"

@Composable
fun MovieCard(modifier: Modifier, movie: Movie) {
    Card(
        modifier = modifier.testTag(MOVIE_CARD_TEST_TAG),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val pictureModifier = Modifier
                .width(108.dp)
                .height(164.dp)
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
            GlideImage(
                modifier = pictureModifier,
                imageModel = movie.poster,
                contentScale = ContentScale.Crop,
                loading = { ImagePlaceholder(modifier = pictureModifier) },
                failure = { ImagePlaceholder(modifier = pictureModifier) },
                previewPlaceholder = R.drawable.ic_launcher_background
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movie.title,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    text = movie.overview,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1
                )
                FlowRow(
                    modifier = Modifier.padding(top = 8.dp),
                    content = {
                        movie.genre.forEach { Chip(it.name.value) }
                    }
                )
            }
        }

    }
}

@Composable
fun ImagePlaceholder(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(64.dp)
            .background(MaterialTheme.colors.background, shape = RoundedCornerShape(8.dp)),
    )
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    SearchWithPaginationTaskTheme {
        MovieCard(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                .defaultMinSize(minHeight = 90.dp)
                .fillMaxWidth(),
            movie = Movie(
                uniqueId = "1",
                movieId = 1,
                title = "Léon: The Professional",
                genre = listOf(
                    Genre(GenreId(1), GenreName("Action")),
                    Genre(GenreId(2), GenreName("Crime")),
                    Genre(GenreId(3), GenreName("Drama")),
                ),
                poster = "https://test.nl",
                overview = "12-year-old Mathilda is reluctantly taken in by Léon, a professional assassin, after her family is murdered. An unusual relationship forms as she becomes his protégée and learns the assassin's trade."
            )
        )
    }
}
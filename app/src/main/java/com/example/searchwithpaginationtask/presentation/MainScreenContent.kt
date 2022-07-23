package com.example.searchwithpaginationtask.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.GenreName
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.MoviesLazyColumn
import com.example.searchwithpaginationtask.presentation.views.SearchField
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainScreenContent(
    searchQuery: String,
    moviesLazyItems: LazyPagingItems<Movie>,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxSize()
    ) {
        SearchField(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            searchQuery = searchQuery,
            onTextChanged = { onTextChanged(it) },
            onClearClick = { onClearClick() })

        MoviesLazyColumn(moviesLazyItems)
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenContentPreview() {
    SearchWithPaginationTaskTheme() {
        MainScreenContent(
            searchQuery = "Léon: The Professional",
            moviesLazyItems = flowOf(
                PagingData.from(
                    listOf(createMovieStub())
                )
            ).collectAsLazyPagingItems(),
            onTextChanged = {},
            onClearClick = {}
        )
    }
}

private fun createMovieStub() = Movie(
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
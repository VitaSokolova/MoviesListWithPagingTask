package com.example.searchwithpaginationtask.stubs

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.GenreName
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class StubMoviesRepository @Inject constructor() : MoviesRepository {

    companion object {
        val stubPagingData = listOf(
            Movie(
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
            ),
            Movie(
                uniqueId = "2",
                movieId = 2,
                title = "Eternal Sunshine of the Spotless Mind",
                genre = listOf(
                    Genre(GenreId(5), GenreName("Romance")),
                    Genre(GenreId(6), GenreName("Sci-fi")),
                ),
                poster = "https://test.nl",
                overview = "Joel and Clementine begin a relationship after a train journey together. However, having had their memories clinically erased, they do not remember their tumultuous past."
            )
        )
    }

    override fun getSearchResults(query: String): Flow<PagingData<Movie>> {
        val loadingComplete = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true),
        )
        return flowOf(
            PagingData.from(
                data = stubPagingData,
                sourceLoadStates = loadingComplete
            )
        )
    }
}
package com.example.searchwithpaginationtask.presentation

import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.GenreName
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import com.example.searchwithpaginationtask.presentation.MainViewModel.Companion.SEARCH_DEBOUNCE_ML
import com.example.searchwithpaginationtask.utils.MainDispatcherRule
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val stubPagingData = PagingData.from(listOf(createMovie()))
    private val moviesRepository: MoviesRepository = mockk(relaxed = true) {
        coEvery { getSearchResults(any()) } returns flowOf(stubPagingData)
    }
    private val viewModel by lazy { MainViewModel(moviesRepository) }

    @Test
    fun `when query is changed should reload the data`() = runTest {
        val updatedQuery = "newValue"

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.movies.collect()
        }
        viewModel.updateQuery(updatedQuery)
        advanceTimeBy(SEARCH_DEBOUNCE_ML + 1)

        coVerify { moviesRepository.getSearchResults(updatedQuery) }
        job.cancel()
    }

    @Test
    fun `when clear query is clicked should clear the search`() = runTest {
        val values = mutableListOf<String>()
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.searchQuery.collect {
                values.add(it)
            }
        }
        viewModel.updateQuery("newValue")
        viewModel.onClearButtonClick()

        Truth.assertThat(values.last()).isEmpty()
        job.cancel()
    }

    private fun createMovie(
        uniqueId: String = "1",
        movieId: Int = 1,
        title: String = "Léon: The Professional",
        genre: List<Genre> = listOf(
            Genre(GenreId(1), GenreName("Action")),
            Genre(GenreId(2), GenreName("Crime")),
            Genre(GenreId(3), GenreName("Drama")),
        ),
        poster: String = "https://test.nl",
        overview: String = "12-year-old Mathilda is reluctantly taken in by Léon, a professional assassin, after her family is murdered. An unusual relationship forms as she becomes his protégée and learns the assassin's trade."

    ): Movie = Movie(uniqueId, movieId, title, poster, overview, genre)
}
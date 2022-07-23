package com.example.searchwithpaginationtask

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.*
import com.example.searchwithpaginationtask.stubs.StubMoviesRepository.Companion.stubPagingData
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class MoviesLazyColumnTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenFirstPageIsLoadingShouldShowFullScreenLoader() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                val mainLoadingState = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true),
                )

                val lazyItems: LazyPagingItems<Movie> = flowOf(
                    PagingData.from(
                        data = emptyList<Movie>(),
                        sourceLoadStates = mainLoadingState
                    )
                ).collectAsLazyPagingItems()
                MoviesLazyColumn(lazyItems)
            }
        }
        composeTestRule.onNodeWithTag(FULLSCREEN_LOADING_ITEM_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun whenNextPageIsLoadingShouldShowLoadingFooter() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                val mainLoadingState = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.Loading
                )
                val lazyItems: LazyPagingItems<Movie> = flowOf(
                    PagingData.from(
                        data = stubPagingData,
                        sourceLoadStates = mainLoadingState
                    )
                ).collectAsLazyPagingItems()
                MoviesLazyColumn(lazyItems)
            }
        }

        val nextItemAfterMoviesIndex = stubPagingData.lastIndex + 1
        composeTestRule.onNodeWithTag(MOVIES_LIST_TEST_TAG)
            .performScrollToIndex(nextItemAfterMoviesIndex)

        composeTestRule.onNodeWithTag(MOVIES_LIST_TEST_TAG)
            .onChildren()[nextItemAfterMoviesIndex]
            .assert(hasTestTag(LOADING_FOOTER_TEST_TAG))
    }

    @Test
    fun whenErrorShouldShowFullScreenLoader() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                val mainErrorState = LoadStates(
                    refresh = LoadState.Error(Throwable()),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true),
                )

                val lazyItems: LazyPagingItems<Movie> = flowOf(
                    PagingData.from(
                        data = emptyList<Movie>(),
                        sourceLoadStates = mainErrorState
                    )
                ).collectAsLazyPagingItems()
                MoviesLazyColumn(lazyItems)
            }
        }
        composeTestRule.onNodeWithTag(ERROR_FOOTER_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun whenNextPageIsLoadingFailsShouldShowErrorFooter() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                val mainLoadingState = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.Error(Throwable())
                )
                val lazyItems: LazyPagingItems<Movie> = flowOf(
                    PagingData.from(
                        data = stubPagingData,
                        sourceLoadStates = mainLoadingState
                    )
                ).collectAsLazyPagingItems()
                MoviesLazyColumn(lazyItems)
            }
        }

        val nextItemAfterMoviesIndex = stubPagingData.lastIndex + 1
        composeTestRule.onNodeWithTag(MOVIES_LIST_TEST_TAG)
            .performScrollToIndex(nextItemAfterMoviesIndex)

        composeTestRule.onNodeWithTag(MOVIES_LIST_TEST_TAG)
            .onChildren()[nextItemAfterMoviesIndex]
            .assert(hasTestTag(ERROR_FOOTER_TEST_TAG))
    }

    @Test
    fun whenDataIsEmptyShouldNothingToShowHeader() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                val nothingIsLoadingState = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
                val lazyItems: LazyPagingItems<Movie> = flowOf(
                    PagingData.empty<Movie>(
                        nothingIsLoadingState,
                        nothingIsLoadingState
                    )
                ).collectAsLazyPagingItems()
                MoviesLazyColumn(lazyItems)
            }
        }

        composeTestRule.onNodeWithTag(NOTHING_TO_SHOW_TEST_TAG).assertIsDisplayed()
    }
}

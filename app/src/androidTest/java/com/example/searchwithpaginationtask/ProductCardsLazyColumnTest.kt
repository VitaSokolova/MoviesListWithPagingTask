package com.example.searchwithpaginationtask

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.*
import com.example.searchwithpaginationtask.stubs.StubProductsRepository.Companion.stubPagingData
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ProductCardsLazyColumnTest {

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

                val lazyItems: LazyPagingItems<Product> = flowOf(
                    PagingData.from(data = emptyList<Product>(), sourceLoadStates = mainLoadingState)
                ).collectAsLazyPagingItems()
                ProductCardsLazyColumn(lazyItems)
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
                val lazyItems: LazyPagingItems<Product> = flowOf(
                    PagingData.from(
                        data = stubPagingData,
                        sourceLoadStates = mainLoadingState
                    )
                ).collectAsLazyPagingItems()
                ProductCardsLazyColumn(lazyItems)
            }
        }

        composeTestRule.onNodeWithTag(PRODUCTS_LIST_TEST_TAG)
            .performScrollToIndex(stubPagingData.lastIndex + 1)

        composeTestRule.onNodeWithTag(PRODUCTS_LIST_TEST_TAG)
            .onChildren()
            .onLast()
            .assert(hasTestTag(LOADING_ITEM_TEST_TAG))
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

                val lazyItems: LazyPagingItems<Product> = flowOf(
                    PagingData.from(data = emptyList<Product>(), sourceLoadStates = mainErrorState)
                ).collectAsLazyPagingItems()
                ProductCardsLazyColumn(lazyItems)
            }
        }
        composeTestRule.onNodeWithTag(ERROR_ITEM_TEST_TAG).assertIsDisplayed()
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
                val lazyItems: LazyPagingItems<Product> = flowOf(
                    PagingData.from(
                        data = stubPagingData,
                        sourceLoadStates = mainLoadingState
                    )
                ).collectAsLazyPagingItems()
                ProductCardsLazyColumn(lazyItems)
            }
        }

        composeTestRule.onNodeWithTag(PRODUCTS_LIST_TEST_TAG)
            .performScrollToIndex(stubPagingData.lastIndex + 1)

        composeTestRule.onNodeWithTag(PRODUCTS_LIST_TEST_TAG)
            .onChildren()
            .onLast()
            .assert(hasTestTag(ERROR_ITEM_TEST_TAG))
    }
}

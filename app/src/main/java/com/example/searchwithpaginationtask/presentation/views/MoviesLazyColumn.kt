package com.example.searchwithpaginationtask.presentation.views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.domain.models.Movie
import kotlinx.coroutines.launch

const val MOVIES_LIST_TEST_TAG = "MOVIES_LIST_TEST_TAG"
const val NOTHING_TO_SHOW_TEST_TAG = "NOTHING_TO_SHOW_TEST_TAG"

@Composable
fun MoviesLazyColumn(lazyItems: LazyPagingItems<Movie>) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.testTag(MOVIES_LIST_TEST_TAG),
        state = listState
    ) {
        items(
            items = lazyItems,
            key = { item -> item.uniqueId },
            itemContent = { item ->
                val modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .defaultMinSize(minHeight = 96.dp)
                    .fillMaxWidth()
                MovieCard(
                    modifier = modifier,
                    movie = item!!
                )
            }
        )
        when {
            lazyItems.loadState.refresh is LoadState.Loading -> {
                coroutineScope.launch {
                    listState.animateScrollToItem(0, 0)
                }
                item { FullscreenLoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            lazyItems.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }
            lazyItems.loadState.refresh is LoadState.Error -> {
                item {
                    ErrorFooter(
                        message = stringResource(id = R.string.error_text),
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { lazyItems.retry() }
                    )
                }
            }
            lazyItems.loadState.append is LoadState.Error -> {
                item {
                    ErrorFooter(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillParentMaxWidth(),
                        message = stringResource(id = R.string.error_text),
                        onClickRetry = { lazyItems.retry() }
                    )
                }
            }
            lazyItems.isNotLoading() && lazyItems.itemCount == 0 -> {
                item {
                    showEmptyResultsText(R.string.nothing_to_show_text)
                }
            }
        }
    }
}

@Composable
private fun showEmptyResultsText(@StringRes textRes: Int) {
    Text(
        modifier = Modifier
            .padding(top = 8.dp)
            .testTag(NOTHING_TO_SHOW_TEST_TAG)
            .fillMaxHeight(),
        color = MaterialTheme.colors.secondary,
        text = stringResource(id = textRes),
    )
}

fun <T : Any> LazyPagingItems<T>.isNotLoading(): Boolean {
    return loadState.refresh is LoadState.NotLoading && loadState.append is LoadState.NotLoading
}
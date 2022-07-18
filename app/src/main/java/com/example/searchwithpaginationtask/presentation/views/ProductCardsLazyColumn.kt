package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.domain.models.Product

const val PRODUCTS_LIST_TEST_TAG = "PRODUCTS_LIST_TEST_TAG"

@Composable
fun ProductCardsLazyColumn(lazyItems: LazyPagingItems<Product>) {
    LazyColumn(modifier = Modifier.testTag(PRODUCTS_LIST_TEST_TAG)) {
        items(
            items = lazyItems,
            key = { item -> item.productId },
            itemContent = { item ->
                val modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                    .defaultMinSize(minHeight = 96.dp)
                    .fillMaxWidth()
                ProductCard(
                    modifier = modifier,
                    product = item!!
                )
            }
        )

        when {
            lazyItems.loadState.refresh is LoadState.Loading -> {
                item { FullscreenLoadingView(modifier = Modifier.fillParentMaxSize()) }
            }
            lazyItems.loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }
            lazyItems.loadState.refresh is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = stringResource(id = R.string.error_text),
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { lazyItems.retry() }
                    )
                }
            }
            lazyItems.loadState.append is LoadState.Error -> {
                item {
                    ErrorItem(
                        modifier = Modifier.fillParentMaxWidth(),
                        message = stringResource(id = R.string.error_text),
                        onClickRetry = { lazyItems.retry() }
                    )
                }
            }
        }
    }
}
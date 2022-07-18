package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme

const val FULLSCREEN_LOADING_ITEM_TEST_TAG = "FULLSCREEN_LOADING_ITEM_TEST_TAG"

@Composable
fun FullscreenLoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.testTag(FULLSCREEN_LOADING_ITEM_TEST_TAG),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun FullscreenLoadingViewPreview() {
    SearchWithPaginationTaskTheme {
        FullscreenLoadingView()
    }
}
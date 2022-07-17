package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme

@Composable
fun FullscreenLoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
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
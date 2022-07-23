package com.example.searchwithpaginationtask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchWithPaginationTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val searchQuery = viewModel.searchQuery.collectAsState("")
                    val moviesLazyItems: LazyPagingItems<Movie> = viewModel.movies.collectAsLazyPagingItems()
                    MainScreenContent(
                        searchQuery = searchQuery.value,
                        moviesLazyItems = moviesLazyItems,
                        onTextChanged = { viewModel.updateQuery(it) },
                        onClearClick = { viewModel.onClearButtonClick() })
                }
            }
        }
    }
}
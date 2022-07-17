package com.example.searchwithpaginationtask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.ProductCardsLazyColumn
import com.example.searchwithpaginationtask.presentation.views.SearchField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchWithPaginationTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        SearchField(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
                                .fillMaxWidth(),
                            queryFlow = viewModel.searchQuery,
                            onTextChanged = { viewModel.updateQuery(it) },
                            onClearClick = { viewModel.onClearButtonClick() })

                        val lazyItems: LazyPagingItems<Product> = viewModel.products.collectAsLazyPagingItems()
                        ProductCardsLazyColumn(lazyItems)
                    }
                }
            }
        }
    }
}
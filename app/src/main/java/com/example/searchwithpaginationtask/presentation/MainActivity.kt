package com.example.searchwithpaginationtask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.Chip
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchWithPaginationTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column() {
                        SearchField(
                            viewModel.searchQuery,
                            { viewModel.updateQuery(it) },
                            { viewModel.onClearButtonClick() })
                        val lazyItems: LazyPagingItems<Product> = viewModel.products.collectAsLazyPagingItems()
                        ProductCardsLazyColumn(lazyItems)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCardsLazyColumn(lazyItems: LazyPagingItems<Product>) {
    LazyColumn {
        items(
            items = lazyItems,
            key = { item -> item.productId },
            itemContent = { item -> ProductCard(item!!) }
        )
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            .defaultMinSize(minHeight = 90.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GlideImage(
                modifier = Modifier
                    .height(150.dp)
                    .width(64.dp)
                    .padding(end = 8.dp),
                imageModel = product.imageUrl,
                contentScale = ContentScale.FillHeight,
                loading = {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                    )
                },
                previewPlaceholder = R.drawable.ic_launcher_background
            )
            Column(verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f, true),
                        text = product.productName,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = stringResource(id = R.string.price_text, product.price),
                        style = MaterialTheme.typography.h6
                    )
                }
                FlowRow(
                    modifier = Modifier.padding(4.dp),
                    content = {
                        product.usp.forEach { Chip(it) }
                    }
                )
            }
        }

    }
}

@Composable
fun SearchField(
    queryFlow: Flow<String>,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    //val inputValue = remember { mutableStateOf(TextFieldValue()) }
    val inputValue = queryFlow.collectAsState("")
    TextField(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
            .fillMaxWidth(),
        value = inputValue.value,
        maxLines = 1,
        onValueChange = { text: String -> onTextChanged(text) },
        placeholder = { Text(text = "Enter user name") },
        trailingIcon = {
            IconButton(
                onClick = { onClearClick() },
                content = {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "clear search query"
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    SearchWithPaginationTaskTheme {
        SearchField(flowOf("Text"), {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    SearchWithPaginationTaskTheme {
        ProductCard(
            Product(
                productId = 1,
                productName = "Apple iPhone 6 32GB Grijs",
                usp = listOf(
                    "32 GB opslagcapaciteit",
                    "4,7 inch Retina HD scherm",
                    "iOS 11"
                ),
                imageUrl = "https://image.coolblue.nl/300x750/products/818870",
                price = 10.5
            )
        )
    }
}
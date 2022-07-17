package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductCard(modifier: Modifier, product: Product) {
    Card(
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    SearchWithPaginationTaskTheme {
        ProductCard(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
                .defaultMinSize(minHeight = 90.dp)
                .fillMaxWidth(),
            product = Product(
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
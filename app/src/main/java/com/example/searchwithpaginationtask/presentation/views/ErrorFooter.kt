package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme

const val ERROR_FOOTER_TEST_TAG = "ERROR_FOOTER_TEST_TAG"

@Composable
fun ErrorFooter(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .testTag(ERROR_FOOTER_TEST_TAG),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(id = R.string.try_again_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorItemPreview() {
    SearchWithPaginationTaskTheme {
        ErrorFooter("Error", onClickRetry = {})
    }
}
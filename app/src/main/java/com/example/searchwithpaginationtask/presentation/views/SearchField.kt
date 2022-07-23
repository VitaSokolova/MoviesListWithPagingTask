package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme

const val SEARCH_FIELD_TEST_TAG = "SEARCH_FIELD_TEST_TAG"

@Composable
fun SearchField(
    modifier: Modifier,
    searchQuery: String,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    TextField(
        modifier = modifier.testTag(SEARCH_FIELD_TEST_TAG),
        value = searchQuery,
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
        SearchField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            searchQuery = "",
            onTextChanged = {},
            onClearClick = {}
        )
    }
}
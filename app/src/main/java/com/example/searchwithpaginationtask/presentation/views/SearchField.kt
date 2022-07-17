package com.example.searchwithpaginationtask.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchwithpaginationtask.R
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchField(
    modifier: Modifier,
    queryFlow: Flow<String>,
    onTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    //val inputValue = remember { mutableStateOf(TextFieldValue()) }
    val inputValue = queryFlow.collectAsState("")
    Column(modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
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

        if (inputValue.value.isEmpty()) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colors.secondary,
                text = stringResource(id = R.string.empty_search_text)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    SearchWithPaginationTaskTheme {
        SearchField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            queryFlow = flowOf(""),
            onTextChanged = {},
            onClearClick = {}
        )
    }
}
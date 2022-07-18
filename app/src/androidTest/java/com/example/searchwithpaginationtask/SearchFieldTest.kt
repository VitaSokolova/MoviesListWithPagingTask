package com.example.searchwithpaginationtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.searchwithpaginationtask.presentation.theme.SearchWithPaginationTaskTheme
import com.example.searchwithpaginationtask.presentation.views.START_SEARCHING_TEXT_TEST_TAG
import com.example.searchwithpaginationtask.presentation.views.SearchField
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class SearchFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenSearchIsEmptyShouldShowStartSearchingState() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    queryFlow = flowOf(""),
                    onTextChanged = { },
                    onClearClick = { }
                )
            }
        }
        composeTestRule.onNodeWithTag(START_SEARCHING_TEXT_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun whenSearchIsNotEmptyShouldHideStartSearchingState() {
        composeTestRule.setContent {
            SearchWithPaginationTaskTheme {
                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    queryFlow = flowOf("Search"),
                    onTextChanged = { },
                    onClearClick = { }
                )
            }
        }

        composeTestRule.onNodeWithTag(START_SEARCHING_TEXT_TEST_TAG)
            .assertDoesNotExist()
    }
}
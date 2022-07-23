package com.example.searchwithpaginationtask

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchwithpaginationtask.presentation.MainActivity
import com.example.searchwithpaginationtask.presentation.MainViewModel.Companion.SEARCH_DEBOUNCE_ML
import com.example.searchwithpaginationtask.presentation.views.MOVIES_LIST_TEST_TAG
import com.example.searchwithpaginationtask.presentation.views.MOVIE_CARD_TEST_TAG
import com.example.searchwithpaginationtask.presentation.views.SEARCH_FIELD_TEST_TAG
import com.example.searchwithpaginationtask.stubs.StubMoviesRepository.Companion.stubPagingData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun whenSearchIsPerformedShouldShowResults() {
        with(composeRule) {
            onNodeWithTag(SEARCH_FIELD_TEST_TAG)
                .performTextInput("Search")

            //skip query input debounce
            mainClock.advanceTimeBy(SEARCH_DEBOUNCE_ML)

            onNodeWithTag(MOVIES_LIST_TEST_TAG)
                .onChildren()
                .filter(hasTestTag(MOVIE_CARD_TEST_TAG))
                .assertCountEquals(stubPagingData.size)
        }
    }
}
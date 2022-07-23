package com.example.searchwithpaginationtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _searchQuery
        .debounce(SEARCH_DEBOUNCE_ML)
        .flatMapLatest { query ->
            moviesRepository.getSearchResults(query)
        }
        .cachedIn(viewModelScope)


    fun updateQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onClearButtonClick() {
        _searchQuery.value = ""
    }

    companion object {
        const val SEARCH_DEBOUNCE_ML = 250L
    }
}
package com.example.searchwithpaginationtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val products: Flow<PagingData<Product>> = _searchQuery
        .debounce(250.milliseconds)
        .flatMapLatest { query ->
            productsRepository.getSearchResults(query)
        }
        .onStart {
            // set up an initial state before the user will type something
            PagingData.empty<Product>()
        }
        .cachedIn(viewModelScope)

    fun updateQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onClearButtonClick() {
        _searchQuery.value = ""
    }
}
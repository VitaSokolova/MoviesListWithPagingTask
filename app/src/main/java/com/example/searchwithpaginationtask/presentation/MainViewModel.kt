package com.example.searchwithpaginationtask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val products: Flow<PagingData<Product>>
        get() = productsRepository.getSearchResults("test")

    fun updateQuery(newQuery: String) {
        _searchQuery.value = newQuery
        Log.v("VITOCHKA", "newQuery=$newQuery")
    }

    fun onClearButtonClick() {
        _searchQuery.value = ""
    }
}
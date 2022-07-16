package com.example.searchwithpaginationtask.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    val products: Flow<PagingData<Product>>
        get() = productsRepository.getSearchResults("test")

}
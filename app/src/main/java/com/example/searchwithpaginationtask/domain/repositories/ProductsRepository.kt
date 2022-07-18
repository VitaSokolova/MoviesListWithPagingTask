package com.example.searchwithpaginationtask.domain.repositories

import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getSearchResults(query: String): Flow<PagingData<Product>>
}
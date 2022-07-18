package com.example.searchwithpaginationtask.stubs

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class StubProductsRepository @Inject constructor(): ProductsRepository {

    companion object {
        val stubPagingData = listOf<Product>(
            Product(1, "productName1", listOf("feature"), "", 10.0),
            Product(2, "productName2", listOf("feature"), "", 10.0),
            Product(3, "productName3", listOf("feature"), "", 10.0),
            Product(4, "productName4", listOf("feature"), "", 10.0),
        )
    }

    override fun getSearchResults(query: String): Flow<PagingData<Product>> {
        val loadingComplete = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true),
        )
        return flowOf(PagingData.from(
            data = stubPagingData,
            sourceLoadStates = loadingComplete
        ))
    }
}
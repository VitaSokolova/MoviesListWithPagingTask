package com.example.searchwithpaginationtask.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.searchwithpaginationtask.data.mappers.toDomain
import com.example.searchwithpaginationtask.domain.models.Product
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsCatalogApi: ProductsCatalogApi
) : ProductsRepository {

    override fun getSearchResults(query: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductsPagingSource(query, productsCatalogApi) }
        ).flow.map { it.map { dto -> dto.toDomain() } }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 23
    }
}
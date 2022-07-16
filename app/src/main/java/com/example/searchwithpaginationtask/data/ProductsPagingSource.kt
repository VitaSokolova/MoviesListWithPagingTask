package com.example.searchwithpaginationtask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchwithpaginationtask.data.models.ProductDto

class ProductsPagingSource(
    private val query: String,
    private val productsCatalogApi: ProductsCatalogApi
) : PagingSource<Int, ProductDto>() {

    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        val currentPageNumber = params.key ?: STARTING_KEY
        return try {
            val newPage = productsCatalogApi.getProducts(query, currentPageNumber)
            val prevKey = if (currentPageNumber == STARTING_KEY) {
                null
            } else {
                currentPageNumber - 1
            }
            val nextKey = if (currentPageNumber < newPage.pageCount) {
                currentPageNumber + 1
            } else {
                null
            }
            LoadResult.Page(newPage.products, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_KEY = 1
    }
}
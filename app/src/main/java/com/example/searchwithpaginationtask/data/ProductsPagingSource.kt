package com.example.searchwithpaginationtask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchwithpaginationtask.data.models.ProductDto
import com.example.searchwithpaginationtask.utils.Logger
import kotlinx.coroutines.CancellationException

class ProductsPagingSource(
    private val query: String,
    private val productsCatalogApi: ProductsCatalogApi,
    private val logger: Logger
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
            val prevKey = getPreviousPageIndex(currentPageNumber)
            if (query.isEmpty()) {
                // imitate real back-end behavior
                LoadResult.Page(emptyList(), prevKey, null)
            } else {
                val newPage = productsCatalogApi.getProducts(query, currentPageNumber)
                val nextKey = getNextPageIndex(currentPageNumber, newPage.pageCount)
                LoadResult.Page(newPage.products, prevKey, nextKey)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logger.error(e)
            LoadResult.Error(e)
        }
    }

    private fun getPreviousPageIndex(currentPageNumber: Int): Int? = if (currentPageNumber == STARTING_KEY) {
        null
    } else {
        currentPageNumber - 1
    }

    private fun getNextPageIndex(
        currentPageNumber: Int,
        totalPageCount: Int
    ): Int? = if (currentPageNumber < totalPageCount) {
        currentPageNumber + 1
    } else {
        null
    }

    companion object {
        private const val STARTING_KEY = 1
    }
}
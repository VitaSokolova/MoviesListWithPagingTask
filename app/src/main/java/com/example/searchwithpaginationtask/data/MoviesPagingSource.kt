package com.example.searchwithpaginationtask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchwithpaginationtask.data.models.MovieWithPageNumberDto
import com.example.searchwithpaginationtask.utils.Logger

class MoviesPagingSource(
    private val query: String,
    private val moviesCatalogApi: MoviesCatalogApi,
    private val logger: Logger
) : PagingSource<Int, MovieWithPageNumberDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieWithPageNumberDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPageIndex = state.pages.indexOf(state.closestPageToPosition(anchorPosition))
            state.pages.getOrNull(anchorPageIndex + 1)?.prevKey ?: state.pages.getOrNull(anchorPageIndex - 1)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieWithPageNumberDto> {
        val currentPageNumber = params.key ?: STARTING_KEY
        val prevKey = getPreviousPageIndex(currentPageNumber)
        return if (query.isEmpty()) {
            LoadResult.Page(emptyList(), prevKey, null)
        } else {
            val newPage = moviesCatalogApi.getMovies( query, currentPageNumber, false)
            val nextKey = getNextPageIndex(currentPageNumber, newPage.totalPages)
            LoadResult.Page(
                newPage.results.map {
                    MovieWithPageNumberDto(
                        movieDto = it,
                        pageNumber = currentPageNumber
                    )
                },
                prevKey,
                nextKey
            )
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
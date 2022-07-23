package com.example.searchwithpaginationtask.domain.repositories

import androidx.paging.PagingData
import com.example.searchwithpaginationtask.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getSearchResults(query: String): Flow<PagingData<Movie>>
}
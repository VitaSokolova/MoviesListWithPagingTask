package com.example.searchwithpaginationtask.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.searchwithpaginationtask.data.mappers.toDomain
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.GenreName
import com.example.searchwithpaginationtask.domain.models.Movie
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import com.example.searchwithpaginationtask.utils.Logger
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesCatalogApi: MoviesCatalogApi,
    private val logger: Logger
) : MoviesRepository {

    private val genresFlow: Flow<Map<GenreId, GenreName>> by lazy {
        flow {
            val genresList = moviesCatalogApi.getGenreList().genres
            val genresMap = genresList.associate { GenreId(it.id) to GenreName(it.name) }
            emit(genresMap)
        }.catch { logger.error(it) }
    }

    override fun getSearchResults(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(query, moviesCatalogApi, logger)
            }
        ).flow
            .zip(genresFlow) { pagingData, genres -> pagingData to genres }
            .map { (pagingData, genres) ->
                pagingData.map { dto ->
                    dto.toDomain { id -> genres[id]?.let { name -> Genre(id, name) } }
                }
            }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 2
    }
}
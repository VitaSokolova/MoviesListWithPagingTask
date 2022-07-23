package com.example.searchwithpaginationtask.data

import com.example.searchwithpaginationtask.data.models.GenresResponseDto
import com.example.searchwithpaginationtask.data.models.MoviesPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesCatalogApi {

    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean
    ): MoviesPageDto

    @GET("genre/movie/list")
    suspend fun getGenreList(): GenresResponseDto
}
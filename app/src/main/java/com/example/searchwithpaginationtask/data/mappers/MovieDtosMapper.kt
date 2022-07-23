package com.example.searchwithpaginationtask.data.mappers

import com.example.searchwithpaginationtask.data.models.MovieWithPageNumberDto
import com.example.searchwithpaginationtask.domain.models.Genre
import com.example.searchwithpaginationtask.domain.models.GenreId
import com.example.searchwithpaginationtask.domain.models.Movie

/**
 * Converts DTO model for movie to domain
 *
 * We have to create uniqueId because of the bug on The Movie Database API side.
 * Sometimes it finishes and starts pages with the same movie.
 * @see <a href="https://trello.com/c/pI6wQtRc/88-search-returning-duplicate-items>Issue Tracker</a>
 */
internal fun MovieWithPageNumberDto.toDomain(genresToDomain: (GenreId) -> Genre?): Movie {
    return Movie(
        uniqueId = "${movieDto.id}" + "$pageNumber",
        movieId = movieDto.id,
        title = movieDto.title,
        poster = movieDto.poster,
        overview = movieDto.overview,
        genre = movieDto.genreIds.mapNotNull { genresToDomain(GenreId(it)) }
    )
}
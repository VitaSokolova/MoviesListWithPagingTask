package com.example.searchwithpaginationtask.domain.models

@JvmInline
value class GenreId(val value: Int)

@JvmInline
value class GenreName(val value: String)

class Genre(
    val id: GenreId,
    val name: GenreName
)
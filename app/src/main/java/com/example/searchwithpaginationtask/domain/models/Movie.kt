package com.example.searchwithpaginationtask.domain.models

class Movie(
    val uniqueId: String,
    val movieId: Int,
    val title: String,
    val poster: String?,
    val overview: String,
    val genre: List<Genre>,
)
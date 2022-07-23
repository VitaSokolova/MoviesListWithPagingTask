package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val poster: String?,
    @SerialName("overview")
    val overview: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
)
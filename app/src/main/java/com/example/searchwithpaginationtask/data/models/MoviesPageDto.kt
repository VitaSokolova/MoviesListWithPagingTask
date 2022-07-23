package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MoviesPageDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
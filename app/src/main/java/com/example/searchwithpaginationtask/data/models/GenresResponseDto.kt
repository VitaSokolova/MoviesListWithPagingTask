package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponseDto(
    @SerialName("genres")
    val genres: List<GenreDto>
)
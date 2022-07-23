package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductsPageDto(
    @SerialName("products")
    val products: List<ProductDto>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("pageSize")
    val pageSize: Int,
    @SerialName("totalResults")
    val totalResults: Int,
    @SerialName("pageCount")
    val pageCount: Int,
)
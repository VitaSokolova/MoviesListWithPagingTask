package com.example.searchwithpaginationtask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductDto(
    @SerialName("productId")
    val productId: Int,
    @SerialName("productName")
    val productName: String,
    @SerialName("USPs")
    val usp: List<String>,
    @SerialName("productImage")
    val imageUrl: String,
    @SerialName("salesPriceIncVat")
    val price: Double
)
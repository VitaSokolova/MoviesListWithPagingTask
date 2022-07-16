package com.example.searchwithpaginationtask.domain.models

data class Product(
    val productId: Int,
    val productName: String,
    val usp: List<String>,
    val imageUrl: String,
    val price: Double
)
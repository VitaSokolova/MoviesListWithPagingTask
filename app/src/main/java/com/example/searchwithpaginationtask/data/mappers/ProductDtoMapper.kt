package com.example.searchwithpaginationtask.data.mappers

import com.example.searchwithpaginationtask.data.models.ProductDto
import com.example.searchwithpaginationtask.domain.models.Product

internal fun ProductDto.toDomain(): Product = Product(
    productId = productId,
    productName = productName,
    usp = usp,
    imageUrl = imageUrl,
    price = price
)
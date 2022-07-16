package com.example.searchwithpaginationtask.data

import com.example.searchwithpaginationtask.data.models.ProductsPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsCatalogApi {

    @GET("search")
    suspend fun getProducts(@Query("query") query: String, @Query("page") page: Int): ProductsPageDto
}
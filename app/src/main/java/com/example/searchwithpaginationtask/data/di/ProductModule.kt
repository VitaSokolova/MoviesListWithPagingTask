package com.example.searchwithpaginationtask.data.di

import com.example.searchwithpaginationtask.data.ProductsCatalogApi
import com.example.searchwithpaginationtask.data.ProductsRepositoryImpl
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    companion object {
        @Provides
        @Singleton
        fun provideProductsCatalogApi(retrofit: Retrofit): ProductsCatalogApi {
            return retrofit.create(ProductsCatalogApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindProductsRepository(productsRepository: ProductsRepositoryImpl): ProductsRepository
}
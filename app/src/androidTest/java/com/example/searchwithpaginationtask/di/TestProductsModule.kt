package com.example.searchwithpaginationtask.di

import com.example.searchwithpaginationtask.data.di.ProductsModule
import com.example.searchwithpaginationtask.domain.repositories.ProductsRepository
import com.example.searchwithpaginationtask.stubs.StubProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProductsModule::class]
)
abstract class TestProductsModule {

    @Singleton
    @Binds
    abstract fun bindTestProductsRepository(repository: StubProductsRepository): ProductsRepository
}

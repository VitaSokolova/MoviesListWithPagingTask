package com.example.searchwithpaginationtask.di

import com.example.searchwithpaginationtask.data.di.MoviesModule
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import com.example.searchwithpaginationtask.stubs.StubMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MoviesModule::class]
)
abstract class TestProductsModule {

    @Singleton
    @Binds
    abstract fun bindTestProductsRepository(repository: StubMoviesRepository): MoviesRepository
}

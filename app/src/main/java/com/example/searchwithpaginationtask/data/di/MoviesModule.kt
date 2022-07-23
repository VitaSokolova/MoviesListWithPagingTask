package com.example.searchwithpaginationtask.data.di

import com.example.searchwithpaginationtask.data.MoviesCatalogApi
import com.example.searchwithpaginationtask.data.MoviesRepositoryImpl
import com.example.searchwithpaginationtask.domain.repositories.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    companion object {
        @Provides
        @Singleton
        fun provideMoviesCatalogApi(retrofit: Retrofit): MoviesCatalogApi {
            return retrofit.create(MoviesCatalogApi::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository
}
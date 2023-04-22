package com.arya.moviecatalogue.di

import android.content.Context
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.room.MoviesDatabase
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.data.source.remote.api.ApiConfig
import com.arya.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {

        val database = MoviesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig)
        val localDataSource = LocalDataSource.getInstance(database.moviesDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
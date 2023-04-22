package com.arya.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: List<MovieEntity>)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM tv_shows")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tv_shows WHERE isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tv_shows WHERE id = :tvShowId")
    fun getTvShow(tvShowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(movie: List<TvShowEntity>)

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateTvShow(movie: TvShowEntity)
}
package com.arya.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.data.source.local.room.MoviesDao

class LocalDataSource private constructor(private val dao: MoviesDao){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(moviesDao)
    }

    fun getMovies(): DataSource.Factory<Int, MovieEntity> = dao.getMovies()
    fun getMovie(movieId: Int): LiveData<MovieEntity> = dao.getMovie(movieId)
    fun insertMovies(movies: List<MovieEntity>) = dao.insertMovies(movies)
    //fun insertMovie(movie: MovieEntity) = dao.insertMovie(movie)
    fun updateMovie(movie: MovieEntity) = dao.updateMovie(movie)
    fun insertFavoriteMovie(movie: MovieEntity, state: Boolean) {
        movie.isFavorite = state
        return dao.updateMovie(movie)
    }
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = dao.getFavoriteMovies()


    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = dao.getTvShows()
    fun getTvShow(tvShowId: Int): LiveData<TvShowEntity> = dao.getTvShow(tvShowId)
    fun insertTvShows(tvShows: List<TvShowEntity>) = dao.insertTvShows(tvShows)
    //fun insertTvShow(tvShow: TvShowEntity) = dao.insertTvShow(tvShow)
    fun updateTvShow(tvShow: TvShowEntity) = dao.updateTvShow(tvShow)
    fun insertFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        tvShow.isFavorite = state
        return dao.updateTvShow(tvShow)
    }
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = dao.getFavoriteTvShows()



}

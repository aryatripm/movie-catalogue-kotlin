package com.arya.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.vo.Resource

interface MovieDataSource {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovie(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getTvShow(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getMoviesFavorite(): LiveData<PagedList<MovieEntity>>
    fun getTvShowsFavorite(): LiveData<PagedList<TvShowEntity>>
    fun insertMovieFavorite(movie: MovieEntity, state: Boolean)
    fun insertTvShowFavorite(tvShow: TvShowEntity, state: Boolean)
}
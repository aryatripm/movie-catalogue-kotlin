package com.arya.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.vo.Resource

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val movieDetailId = MutableLiveData<Int>()

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getMovies()
    fun getMovie(id: Int): LiveData<Resource<MovieEntity>> = movieRepository.getMovie(id)

    fun getMoviesFavorite(): LiveData<PagedList<MovieEntity>> = movieRepository.getMoviesFavorite()
    fun insertMovieFavorite(movie: MovieEntity, state: Boolean) = movieRepository.insertMovieFavorite(movie, state)

    fun setSelectedMovie(movieId: Int) {
        movieDetailId.value = movieId
    }

    fun setMovieFavorite() {
        val movie = movieDetail.value?.data
        if (movie != null) {
            val newState = !movie.isFavorite
            insertMovieFavorite(movie, newState)
        }
    }

    fun setFavorite (movie: MovieEntity) {
        val newState = !movie.isFavorite
        insertMovieFavorite(movie, newState)
    }

    var movieDetail: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(movieDetailId) { movieId ->
            movieRepository.getMovie(movieId)
        }

}
package com.arya.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.vo.Resource

class TvShowViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val tvShowDetailId = MutableLiveData<Int>()

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = movieRepository.getTvShows()
    fun getTvShow(id: Int): LiveData<Resource<TvShowEntity>> = movieRepository.getTvShow(id)

    fun getTvShowsFavorite(): LiveData<PagedList<TvShowEntity>> = movieRepository.getTvShowsFavorite()
    fun insertTvShowFavorite(tvShow: TvShowEntity, state: Boolean) = movieRepository.insertTvShowFavorite(tvShow, state)

    fun setSelectedTvShow(tvShowId: Int) {
        tvShowDetailId.value = tvShowId
    }

    fun setTvShowFavorite() {
        val tvShow = tvShowDetail.value?.data
        if (tvShow != null) {
            val newState = !tvShow.isFavorite
            insertTvShowFavorite(tvShow, newState)
        }
    }

    fun setFavorite (tvShow: TvShowEntity) {
        val newState = !tvShow.isFavorite
        movieRepository.insertTvShowFavorite(tvShow, newState)
    }

    var tvShowDetail: LiveData<Resource<TvShowEntity>> =
        Transformations.switchMap(tvShowDetailId) { tvShowId ->
            movieRepository.getTvShow(tvShowId)
        }

}
package com.arya.moviecatalogue.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.data.source.remote.ApiResponse
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.data.source.remote.response.MovieResponse
import com.arya.moviecatalogue.utils.AppExecutors
import com.arya.moviecatalogue.vo.Resource

class MovieRepository private constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource, private val appExecutors: AppExecutors): MovieDataSource{

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MovieRepository =
                instance ?: synchronized(this) {
                    instance ?: MovieRepository(remoteData, localData, appExecutors).apply { instance = this }
                }
    }

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<MovieEntity>) {
                val moviesList = ArrayList<MovieEntity>()
                for (response in data) {
                    Log.d("AAA", response.toString())
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        response.tagline,
                        response.overview,
                        response.release_date,
                        // response.genres,
                        response.runtime,
                        response.vote_average,
                        response.poster_path,
                        false
                    )
                    moviesList.add(movie)
                }
                localDataSource.insertMovies(moviesList)
            }


        }.asLiveData()
    }

    override fun getMovie(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieEntity>> {
                return remoteDataSource.getMovie(movieId)
            }

            override fun saveCallResult(data: MovieEntity) {
                val movie = MovieEntity(
                    data.id,
                    data.title,
                    data.tagline,
                    data.overview,
                    data.release_date,
                    // data.genres,
                    data.runtime,
                    data.vote_average,
                    data.poster_path,
                    false
                )
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> {
                return remoteDataSource.getTvShows()
            }

            override fun saveCallResult(data: List<TvShowEntity>) {
                val tvShowsList = ArrayList<TvShowEntity>()
                for (response in data) {
                    Log.d("AAA", response.toString())
                    val tvShow = TvShowEntity(
                        response.id,
                        response.original_name,
                        response.tagline,
                        response.overview,
                        response.first_air_date,
                        // response.genres,
                        // response.episode_run_time,
                        response.vote_average,
                        response.poster_path,
                        false
                    )
                    tvShowsList.add(tvShow)
                }
                localDataSource.insertTvShows(tvShowsList)
            }

        }.asLiveData()
    }

    override fun getTvShow(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowEntity>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getTvShow(tvShowId)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowEntity>> {
                return remoteDataSource.getTvShow(tvShowId)
            }

            override fun saveCallResult(data: TvShowEntity) {
                val tvShow = TvShowEntity(
                    data.id,
                    data.original_name,
                    data.tagline,
                    data.overview,
                    data.first_air_date,
                    // data.genres,
                    //data.episode_run_time,
                    data.vote_average,
                    data.poster_path,
                    false
                )
                localDataSource.updateTvShow(tvShow)
            }

        }.asLiveData()
    }

    override fun getMoviesFavorite(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getTvShowsFavorite(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun insertMovieFavorite(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.insertFavoriteMovie(movie, state)
        }
    }

    override fun insertTvShowFavorite(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.insertFavoriteTvShow(tvShow, state)
        }
    }


}
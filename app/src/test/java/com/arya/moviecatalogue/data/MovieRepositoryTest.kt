package com.arya.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arya.moviecatalogue.data.source.local.LocalDataSource
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.data.source.remote.RemoteDataSource
import com.arya.moviecatalogue.utils.AppExecutors
import com.arya.moviecatalogue.utils.PagedListUtil
import com.arya.moviecatalogue.vo.Resource
import org.junit.Assert.*
import org.junit.Rule


import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.*

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieList = DataDummy.getMovies()
    private val movieId = movieList[0].id
    private val tvShowList = DataDummy.getTvShows()
    private val tvShowId = tvShowList[0].id


    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies()).thenReturn(dataSourceFactory)
        movieRepository.getMovies()

        val movies = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getMovies()
        assertNotNull(movies.data)
        assertEquals(movieList.size.toLong(), movies.data?.size?.toLong())
    }

    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = DataDummy.getMovies()[0]
        `when`(local.getMovie(movieId)).thenReturn(movie)

        val movieEntity = LiveDataTest.getValue(movieRepository.getMovie(movieId)).data
        verify(local).getMovie(movieId)
        val movieResponse = movieList[0]
        assertNotNull(movieEntity)
        if (movieEntity != null) {
            assertEquals(movieResponse.title, movieEntity.title)
            assertEquals(movieResponse.overview, movieEntity.overview)
            assertEquals(movieResponse.release_date, movieEntity.release_date)
            assertEquals(movieResponse.vote_average, movieEntity.vote_average)
            assertEquals(movieResponse.id, movieEntity.id)
            assertEquals(movieResponse.runtime, movieEntity.runtime)
            assertEquals(movieResponse.tagline, movieEntity.tagline)
            assertEquals(movieResponse.isFavorite, movieEntity.isFavorite)
            assertEquals(movieResponse.poster_path, movieEntity.poster_path)
        }
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getTvShows()

        val tvShow = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShows()))
        verify(local).getTvShows()
        assertNotNull(tvShow.data)
        assertEquals(tvShowList.size.toLong(), tvShow.data?.size?.toLong())
    }

    @Test
    fun getTvShow() {
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = DataDummy.getTvShows()[0]
        `when`(local.getTvShow(tvShowId)).thenReturn(tvShow)

        val tvShowEntity = LiveDataTest.getValue(movieRepository.getTvShow(tvShowId)).data
        verify(local).getTvShow(tvShowId)
        val tvShowResponse = tvShowList[0]
        assertNotNull(tvShowEntity)
        if (tvShowEntity != null) {
            assertEquals(tvShowResponse.original_name, tvShowEntity.original_name)
            assertEquals(tvShowResponse.overview, tvShowEntity.overview)
            assertEquals(tvShowResponse.first_air_date, tvShowEntity.first_air_date)
            assertEquals(tvShowResponse.vote_average, tvShowEntity.vote_average)
            assertEquals(tvShowResponse.id, tvShowEntity.id)
            assertEquals(tvShowResponse.tagline, tvShowEntity.tagline)
            assertEquals(tvShowResponse.isFavorite, tvShowEntity.isFavorite)
            assertEquals(tvShowResponse.poster_path, tvShowEntity.poster_path)
        }
    }

    @Test
    fun getMoviesFavorite() {
        val moviesFavorite = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(moviesFavorite)
        movieRepository.getMoviesFavorite()

        val favoriteMovieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getMovies()))
        verify(local).getFavoriteMovies()
        assertNotNull(favoriteMovieEntities.data)
        assertEquals(movieList.size.toLong(), favoriteMovieEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowsFavorite() {
        val tvShowsFavorite = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(tvShowsFavorite)
        movieRepository.getTvShowsFavorite()

        val favoriteTvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.getTvShows()))
        verify(local).getFavoriteTvShows()
        assertNotNull(favoriteTvShowEntities.data)
        assertEquals(tvShowList.size.toLong(), favoriteTvShowEntities.data?.size?.toLong())
    }
}
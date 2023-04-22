package com.arya.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.DataDummy
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.vo.Resource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observer2: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observer3: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
        viewModel.setSelectedMovie(DataDummy.getMovies()[0].id)
    }

    @Test
    fun getMovies() {
        val movies = Resource.success(pagedList)
        `when`(movies.data?.size).thenReturn(2)
        val movies2 = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies2.value = movies

        `when`(repository.getMovies()).thenReturn(movies2)
        val movies3 = viewModel.getMovies().value?.data
        verify(repository).getMovies()
        assertNotNull(movies3)
        assertEquals(movies3?.size, 2)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movies)
    }

    @Test
    fun getMovie() {
        val movieDetail = Resource.success(DataDummy.getMovies()[0])
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = movieDetail

        `when`(repository.getMovie(DataDummy.getMovies()[0].id)).thenReturn(movie)
        viewModel.movieDetail.observeForever(observer2)
        verify(observer2).onChanged(movieDetail)

        val id = movie.value!!.data!!.id
        assertEquals(movie.value!!.data?.id, viewModel.getMovie(id).value?.data?.id)
        assertEquals(movie.value!!.data?.title, viewModel.getMovie(id).value?.data?.title)
        assertEquals(movie.value!!.data?.tagline, viewModel.getMovie(id).value?.data?.tagline)
        assertEquals(movie.value!!.data?.overview, viewModel.getMovie(id).value?.data?.overview)
        assertEquals(movie.value!!.data?.release_date, viewModel.getMovie(id).value?.data?.release_date)
        assertEquals(movie.value!!.data?.runtime, viewModel.getMovie(id).value?.data?.runtime)
        assertEquals(movie.value!!.data?.vote_average, viewModel.getMovie(id).value?.data?.vote_average)
        assertEquals(movie.value!!.data?.poster_path, viewModel.getMovie(id).value?.data?.poster_path)
    }

    @Test
    fun getMoviesFavorite() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(2)
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = dummyMovies

        `when`(repository.getMoviesFavorite()).thenReturn(movie)
        val movieEntities = viewModel.getMoviesFavorite().value
        verify(repository).getMoviesFavorite()
        assertNotNull(movieEntities)
        assertEquals(2, movieEntities?.size)

        viewModel.getMoviesFavorite().observeForever(observer3)
        verify(observer3).onChanged(dummyMovies)
    }
}
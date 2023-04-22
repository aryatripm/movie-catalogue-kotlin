package com.arya.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.arya.moviecatalogue.data.DataDummy
import com.arya.moviecatalogue.data.MovieRepository
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
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
class TvShowViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var observer2: Observer<Resource<TvShowEntity>>

    @Mock
    private lateinit var observer3: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
        viewModel.setSelectedTvShow(DataDummy.getTvShows()[0].id)
    }

    @Test
    fun getTvShows() {
        val tvShows = Resource.success(pagedList)
        `when`(tvShows.data?.size).thenReturn(2)
        val tvShows2 = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows2.value = tvShows

        `when`(repository.getTvShows()).thenReturn(tvShows2)
        val tvShows3 = viewModel.getTvShows().value?.data
        verify(repository).getTvShows()
        assertNotNull(tvShows3)
        assertEquals(tvShows3?.size, 2)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(tvShows)
    }

    @Test
    fun getTvShow() {
        val tvShowDetail = Resource.success(DataDummy.getTvShows()[0])
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = tvShowDetail

        `when`(repository.getTvShow(tvShow.value!!.data!!.id)).thenReturn(tvShow)
        viewModel.tvShowDetail.observeForever(observer2)
        verify(observer2).onChanged(tvShowDetail)

        val id = tvShow.value!!.data!!.id
        assertEquals(tvShow.value!!.data?.id, viewModel.getTvShow(id).value?.data?.id)
        assertEquals(tvShow.value!!.data?.original_name, viewModel.getTvShow(id).value?.data?.original_name)
        assertEquals(tvShow.value!!.data?.tagline, viewModel.getTvShow(id).value?.data?.tagline)
        assertEquals(tvShow.value!!.data?.overview, viewModel.getTvShow(id).value?.data?.overview)
        assertEquals(tvShow.value!!.data?.first_air_date, viewModel.getTvShow(id).value?.data?.first_air_date)
        assertEquals(tvShow.value!!.data?.vote_average, viewModel.getTvShow(id).value?.data?.vote_average)
        assertEquals(tvShow.value!!.data?.poster_path, viewModel.getTvShow(id).value?.data?.poster_path)
    }

    @Test
    fun getTvShowsFavorite() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(2)
        val tvShow = MutableLiveData<PagedList<TvShowEntity>>()
        tvShow.value = dummyTvShows

        `when`(repository.getTvShowsFavorite()).thenReturn(tvShow)
        val tvShowEntities = viewModel.getTvShowsFavorite().value
        verify(repository).getTvShowsFavorite()
        assertNotNull(tvShowEntities)
        assertEquals(2, tvShowEntities?.size)

        viewModel.getTvShowsFavorite().observeForever(observer3)
        verify(observer3).onChanged(dummyTvShows)
    }
}
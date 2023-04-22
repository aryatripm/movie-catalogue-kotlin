package com.arya.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.data.source.remote.api.ApiConfig
import com.arya.moviecatalogue.data.source.remote.response.MovieResponse
import com.arya.moviecatalogue.data.source.remote.response.TvShowResponse
import com.arya.moviecatalogue.utils.Constant
import com.arya.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiConfig: ApiConfig){

    // private val apiConfig = ApiConfig

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiConfig: ApiConfig): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource(apiConfig).apply {
                instance = this
            }
        }
    }

    interface GetMoviesCallback{
        fun onResponse(movieResponse: List<MovieEntity>)
    }

    interface GetMovieCallback {
        fun onResponse(movieResponse: MovieEntity)
    }

    interface GetTvShowsCallback{
        fun onResponse(tvShowResponse: List<TvShowEntity>)
    }

    interface GetTvShowCallback {
        fun onResponse(tvShowResponse: TvShowEntity)
    }



    fun getMovies(): LiveData<ApiResponse<List<MovieEntity>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieEntity>>>()

        apiConfig.getApiService().getMovies(Constant.API, Constant.LANG, 1).enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("AAA", response.toString())
                resultMovies.value = ApiResponse.success(response.body()?.results as List<MovieEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("TAG", t.toString())
                EspressoIdlingResource.decrement()
            }

        })
        return resultMovies
    }

    fun getMovie(id: Int): LiveData<ApiResponse<MovieEntity>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MovieEntity>>()

        apiConfig.getApiService().getMovie(id, Constant.API, Constant.LANG).enqueue(object: Callback<MovieEntity> {
            override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                resultMovie.value = ApiResponse.success(response.body()!!)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                Log.d("TAG", t.toString())
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovie
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvShowEntity>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowEntity>>>()

        apiConfig.getApiService().getTvShows(Constant.API, Constant.LANG, 1).enqueue(object: Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                Log.d("AAA", response.toString())
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<TvShowEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("TAG", t.toString())
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShows
    }

    fun getTvShow(id: Int) : LiveData<ApiResponse<TvShowEntity>> {
        EspressoIdlingResource.increment()
        val resultTvShow =  MutableLiveData<ApiResponse<TvShowEntity>>()

        apiConfig.getApiService().getTvShow(id, Constant.API, Constant.LANG).enqueue(object: Callback<TvShowEntity> {
            override fun onResponse(call: Call<TvShowEntity>, response: Response<TvShowEntity>) {
                resultTvShow.value = ApiResponse.success(response.body()!!)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                Log.d("TAG", t.toString())
                EspressoIdlingResource.decrement()
            }

        })
        return resultTvShow
    }
}
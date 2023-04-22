package com.arya.moviecatalogue.data.source.remote.api

import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity
import com.arya.moviecatalogue.data.source.remote.response.MovieResponse
import com.arya.moviecatalogue.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Call<MovieResponse>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String,
    ): Call<MovieEntity>

    @GET("tv/popular")
    fun getTvShows(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Call<TvShowResponse>

    @GET("tv/{id}")
    fun getTvShow(
        @Path("id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String,
    ): Call<TvShowEntity>

}
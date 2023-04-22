package com.arya.moviecatalogue.data.source.remote.response

import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.remote.ApiResponse

data class MovieResponse(
        val results: List<MovieEntity>
)

package com.arya.moviecatalogue.data.source.remote.response

import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity

data class TvShowResponse(
        val results: List<TvShowEntity>
)

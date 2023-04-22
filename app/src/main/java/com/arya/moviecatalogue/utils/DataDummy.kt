package com.arya.moviecatalogue.utils

import com.arya.moviecatalogue.data.source.local.entity.Genres
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity

object DataDummy {

    fun getMovies(): List<MovieEntity> = arrayListOf(
        MovieEntity(
            425909,
            "Ghostbusters: Afterlife",
            "",
            "When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind.",
            "2021-11-11",
            124,
            7.7f,
            "/sg4xJaufDiQl7caFEskBtQXfD4x.jpg"
        ),
        MovieEntity(
            0,
            "",
            "",
            "",
            "",
            0,
            0.0f,
            ""
        )
    )

    fun getTvShows(): List<TvShowEntity> = arrayListOf(
        TvShowEntity(
            1416,
            "Grey's Anatomy",
            "",
            "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
            "2005-03-27",
            8.2f,
            "/zPIug5giU8oug6Xes5K1sTfQJxY.jpg"
        ),
        TvShowEntity(
            0,
            "",
            "",
            "",
            "",
            0.0f,
            ""
        )
    )
}
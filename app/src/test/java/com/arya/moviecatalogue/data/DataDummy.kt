package com.arya.moviecatalogue.data

import com.arya.moviecatalogue.data.source.local.entity.Genres
import com.arya.moviecatalogue.data.source.local.entity.MovieEntity
import com.arya.moviecatalogue.data.source.local.entity.TvShowEntity

object DataDummy {

    fun getMovies(): List<MovieEntity> = arrayListOf(
        MovieEntity(
            634649,
            "Spider-Man: No Way Home",
            "The Multiverse unleashed.",
            "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            "2021-12-15",
            148,
            8.4f,
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
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
            77169,
            "Cobra Kai",
            "Fight for the soul of the valley.",
            "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
            "2018-05-02",
            8.1f,
            "/6POBWybSBDBKjSs1VAQcnQC1qyt.jpg"
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
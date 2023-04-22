package com.arya.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tv_shows")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val original_name: String,
    val tagline: String?,
    val overview: String,
    val first_air_date: String,
    // val genres: List<Genres>,
    // val episode_run_time: List<Int>,
    val vote_average: Float,
    val poster_path: String,
    var isFavorite: Boolean = false
) : Parcelable

// TODO Hapus tagline, runtime, genre
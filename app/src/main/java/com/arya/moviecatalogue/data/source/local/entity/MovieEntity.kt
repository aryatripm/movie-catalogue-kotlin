package com.arya.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String,
    val release_date: String,
    // val genres: List<Genres>,
    val runtime: Int,
    val vote_average: Float,
    val poster_path: String,
    var isFavorite: Boolean = false
) : Parcelable
package com.arya.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    val id: Int,
    val name: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}

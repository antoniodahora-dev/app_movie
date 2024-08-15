package com.a3tecnology.appmovie.presenter.model

import android.os.Parcelable
import com.a3tecnology.appmovie.domain.model.movie.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesByGenre(
    val id: Int?,
    val name: String?,
    val movies: List<Movie>?,
): Parcelable

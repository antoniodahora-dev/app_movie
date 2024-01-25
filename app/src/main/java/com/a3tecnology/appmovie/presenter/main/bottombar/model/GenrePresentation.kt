package com.a3tecnology.appmovie.presenter.main.bottombar.model

import android.os.Parcelable
import com.a3tecnology.appmovie.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenrePresentation(
    val id: Int?,
    val name: String?,
    val movies: List<Movie>?,
): Parcelable

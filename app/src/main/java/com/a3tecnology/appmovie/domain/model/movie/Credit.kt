package com.a3tecnology.appmovie.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credit(
    val cast: List<Person>?
): Parcelable
package com.a3tecnology.appmovie.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorDetails (

    val name: String?,

    val username: String?,

    val avatarPath: String?,

    val rating: Int?

): Parcelable
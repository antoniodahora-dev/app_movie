package com.a3tecnology.appmovie.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieReview(
    val author: String?,

    val authorDetails: AuthorDetails?,

    val content: String?,

    val createdAt: String?,

    val id: String?,

    val updatedAt: String?,

    val url: String?
): Parcelable
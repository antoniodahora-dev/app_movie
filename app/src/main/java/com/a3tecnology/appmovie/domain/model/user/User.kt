package com.a3tecnology.appmovie.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//aula 367.112
@Parcelize
data class User(
    val id: String? = null,
    val photoUrl: String? = null,
    val firstName: String? = null,
    val surName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val genre: String? = null,
    val country: String? = null
): Parcelable

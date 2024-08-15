package com.a3tecnology.appmovie.data.model.movie

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenreResponse>?
)

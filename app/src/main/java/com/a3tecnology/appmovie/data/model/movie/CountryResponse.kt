package com.a3tecnology.appmovie.data.model.movie

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("name")
    val name: String?
)

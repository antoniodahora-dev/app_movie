package com.a3tecnology.appmovie.data.model

import com.google.gson.annotations.SerializedName

data class CreditResponse(

    @SerializedName("cast")
    val cast: List<PersonResponse>?

)
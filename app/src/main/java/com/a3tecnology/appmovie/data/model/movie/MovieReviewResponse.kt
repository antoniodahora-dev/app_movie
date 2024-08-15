package com.a3tecnology.appmovie.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("author")
    val author: String?,

    @SerializedName("author_details")
    val authorDetailsResponse: AuthorDetailsResponse?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("id")
    val id: String?,

    @SerializedName("update_at")
    val updatedAt: String?,

    @SerializedName("url")
    val url: String?
)
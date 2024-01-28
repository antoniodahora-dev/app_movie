package com.a3tecnology.appmovie.domain.repository.movie

import com.a3tecnology.appmovie.data.model.CreditResponse
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse

interface MovieDetailsRepository {

    suspend fun getMovieDetails(
        apiKey: String,
        language: String?,
        movieId: Int?): MovieResponse

    suspend fun getCredits(
        apiKey: String,
        language: String?,
        movieId: Int?): CreditResponse
}
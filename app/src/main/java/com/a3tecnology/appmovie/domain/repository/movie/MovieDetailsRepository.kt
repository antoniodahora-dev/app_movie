package com.a3tecnology.appmovie.domain.repository.movie

import com.a3tecnology.appmovie.data.model.movie.CreditResponse
import com.a3tecnology.appmovie.data.model.movie.MovieResponse
import com.a3tecnology.appmovie.data.model.movie.MovieReviewResponse

interface MovieDetailsRepository {

    suspend fun getMovieDetails(movieId: Int?): MovieResponse

    suspend fun getCredits( movieId: Int?): CreditResponse

    suspend fun getSimilar(movieId: Int?): List<MovieResponse>

    suspend fun getMovieReviews(movieId: Int?): List<MovieReviewResponse>
}
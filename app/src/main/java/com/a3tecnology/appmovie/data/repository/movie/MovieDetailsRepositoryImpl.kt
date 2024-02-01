package com.a3tecnology.appmovie.data.repository.movie

import com.a3tecnology.appmovie.data.api.ServiceApi
import com.a3tecnology.appmovie.data.model.CreditResponse
import com.a3tecnology.appmovie.data.model.MovieResponse
import com.a3tecnology.appmovie.data.model.MovieReviewResponse
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(
        apiKey: String,
        language: String?,
        movieId: Int?
    ): MovieResponse {
        return serviceApi.getMovieDetails(
            apiKey = apiKey,
            movieId = movieId,
            language = language
        )
    }

    override suspend fun getCredits(
        apiKey: String,
        language: String?,
        movieId: Int?
    ): CreditResponse {
        return serviceApi.getCredits(
            apiKey = apiKey,
            movieId = movieId,
            language = language
        )
    }

    override suspend fun getSimilar(
        apiKey: String,
        language: String?,
        movieId: Int?
    ): List<MovieResponse> {
        return serviceApi.getSimilar(
            apiKey = apiKey,
            movieId = movieId,
            language = language
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        apiKey: String,
        language: String?,
        movieId: Int?
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            apiKey = apiKey,
            movieId = movieId,
            language = language
        ).results ?: emptyList()
    }
}
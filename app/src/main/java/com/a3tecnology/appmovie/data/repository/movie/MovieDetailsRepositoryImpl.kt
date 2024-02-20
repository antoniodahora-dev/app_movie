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
         movieId: Int?
    ): MovieResponse {
        return serviceApi.getMovieDetails(
            movieId = movieId
        )
    }

    override suspend fun getCredits(
        movieId: Int?
    ): CreditResponse {
        return serviceApi.getCredits(
            movieId = movieId,
        )
    }

    override suspend fun getSimilar(
          movieId: Int?
    ): List<MovieResponse> {
        return serviceApi.getSimilar(
            movieId = movieId
        ).results ?: emptyList()
    }

    override suspend fun getMovieReviews(
        movieId: Int?
    ): List<MovieReviewResponse> {
        return serviceApi.getMovieReviews(
            movieId = movieId
        ).results ?: emptyList()
    }
}
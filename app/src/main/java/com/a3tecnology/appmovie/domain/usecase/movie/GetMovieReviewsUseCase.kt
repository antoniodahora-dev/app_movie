package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.MovieReview
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke( movieId: Int?): List<MovieReview> {
        return repository.getMovieReviews(movieId = movieId
        ).map { it.toDomain() }
    }
}
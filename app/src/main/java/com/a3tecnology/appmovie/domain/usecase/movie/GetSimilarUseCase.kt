package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Credit
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetSimilarUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int?): List<Movie> {
        return repository.getSimilar(
            movieId = movieId
        ).map { it.toDomain() }.filter { it.posterPath != null }
    }
}
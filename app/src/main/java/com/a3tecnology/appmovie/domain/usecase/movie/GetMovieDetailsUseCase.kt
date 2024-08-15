package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.movie.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int?): Movie {
        return repository.getMovieDetails(
            movieId = movieId
        ).toDomain()
    }
}
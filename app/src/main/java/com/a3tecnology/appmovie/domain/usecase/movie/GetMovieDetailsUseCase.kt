package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, language: String?, movieId: Int?): Movie {
        return repository.getMovieDetails(
            apiKey = apiKey,
            language = language,
            movieId = movieId
        ).toDomain()
    }
}
package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, language: String?, genreId: Int?): List<Movie> {
        return repository.getMovieByGenre(
            apiKey = apiKey,
            language = language,
            genreId =  genreId
        ).map { it.toDomain() }
    }
}
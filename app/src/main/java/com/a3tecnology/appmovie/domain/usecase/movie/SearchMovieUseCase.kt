package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, language: String?, query: String?): List<Movie> {
        return repository.searchMovie(
            apiKey = apiKey,
            language = language,
            query = query
        ).filter { it.backdropPath != null}.map {  it.toDomain() }
    }
}
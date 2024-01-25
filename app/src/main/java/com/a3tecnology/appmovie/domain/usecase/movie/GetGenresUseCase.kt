package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(apiKey: String, language: String?): List<Genre> {
        return repository.getGenres(
            apiKey = apiKey,
            language = language
        ).genres?.map { it.toDomain() } ?: emptyList()
    }
}
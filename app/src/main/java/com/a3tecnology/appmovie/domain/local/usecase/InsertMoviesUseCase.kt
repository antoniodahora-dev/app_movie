package com.a3tecnology.appmovie.domain.local.usecase

import com.a3tecnology.appmovie.data.mapper.toEntity
import com.a3tecnology.appmovie.domain.local.repository.MovieLocalRepository
import com.a3tecnology.appmovie.domain.model.movie.Movie
import javax.inject.Inject

class InsertMoviesUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movie: Movie) {
        return repository.insertMovies(movie.toEntity())
    }
}
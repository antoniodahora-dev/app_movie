package com.a3tecnology.appmovie.domain.local.usecase

import com.a3tecnology.appmovie.domain.local.repository.MovieLocalRepository
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    suspend operator fun invoke(movieId: Int?) {
        return repository.deleteMovies(movieId)
    }
}
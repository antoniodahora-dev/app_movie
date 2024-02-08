package com.a3tecnology.appmovie.domain.local.usecase

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.local.repository.MovieLocalRepository
import com.a3tecnology.appmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieLocalRepository
) {
    operator fun invoke() : Flow<List<Movie>> {
        return repository.getMovies().map { movieList ->
            movieList.map { it.toDomain() }
        }
    }
}
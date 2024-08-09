package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.model.MovieResponse
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject



class GetMovieByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(genreId: Int?): List<MovieResponse> {
        return repository.getMovieByGenre(genreId).results ?: emptyList()
    }

}
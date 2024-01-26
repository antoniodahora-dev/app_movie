package com.a3tecnology.appmovie.domain.repository.movie

import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(
        apiKey: String,
        language:
        String?): GenresResponse

    suspend fun getMovieByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?): List<MovieResponse>

    suspend fun searchMovie(
        apiKey: String,
        language: String?,
        query: String?): List<MovieResponse>

    suspend fun getMovieDetails(
        apiKey: String,
        language: String?,
        movieId: Int?): MovieResponse
}
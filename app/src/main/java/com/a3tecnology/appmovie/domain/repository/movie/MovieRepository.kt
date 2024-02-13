package com.a3tecnology.appmovie.domain.repository.movie

import androidx.paging.PagingSource
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(
        apiKey: String?,
        language: String?): GenresResponse

     fun getMovieByGenre(
        apiKey: String?,
        language: String?,
        genreId: Int?
     ): PagingSource<Int, MovieResponse>

    suspend fun searchMovie(
        apiKey: String?,
        language: String?,
        query: String?): List<MovieResponse>

}
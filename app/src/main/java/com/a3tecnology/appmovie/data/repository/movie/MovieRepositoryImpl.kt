package com.a3tecnology.appmovie.data.repository.movie

import androidx.paging.PagingSource
import com.a3tecnology.appmovie.data.api.ServiceApi
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse
import com.a3tecnology.appmovie.data.paging.MovieByGenrePagingSource
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieRepository {

    override suspend fun getGenres(
        apiKey: String?,
        language: String?): GenresResponse {
        return serviceApi.getGenres(
            apiKey = apiKey,
            language = language
        )
    }


    override fun getMovieByGenre(
        apiKey: String?,
        language: String?,
        genreId: Int?
    ): PagingSource<Int, MovieResponse> {
        return MovieByGenrePagingSource(serviceApi, genreId)
    }

    override suspend fun searchMovie(
        apiKey: String?,
        language: String?,
        query: String?
    ): List<MovieResponse> {
        return serviceApi.searchMovie(
            apiKey = apiKey,
            language = language,
            query = query
        ).results ?: emptyList()
    }

}
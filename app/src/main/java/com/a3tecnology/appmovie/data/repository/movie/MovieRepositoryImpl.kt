package com.a3tecnology.appmovie.data.repository.movie

import androidx.paging.PagingSource
import com.a3tecnology.appmovie.data.api.ServiceApi
import com.a3tecnology.appmovie.data.model.BasePaginationRemote
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse
import com.a3tecnology.appmovie.data.paging.MovieByGenrePagingSource
import com.a3tecnology.appmovie.data.paging.SearchMoviePagingSource
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : MovieRepository {

    override suspend fun getGenres(): GenresResponse {
        return serviceApi.getGenres()
    }


    override fun getMovieByGenrePagination(genreId: Int?): PagingSource<Int, MovieResponse> {
        return MovieByGenrePagingSource(serviceApi, genreId)
    }

    override suspend fun getMovieByGenre(genreId: Int?): BasePaginationRemote<List<MovieResponse>> {
      return serviceApi.getMovieByGenre(genreId)
    }

    override fun searchMovie(query: String?): PagingSource<Int, MovieResponse> {
        return SearchMoviePagingSource(serviceApi, query)
    }

}
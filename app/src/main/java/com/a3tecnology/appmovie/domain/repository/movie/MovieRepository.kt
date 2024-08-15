package com.a3tecnology.appmovie.domain.repository.movie

import androidx.paging.PagingSource
import com.a3tecnology.appmovie.data.model.movie.BasePaginationRemote
import com.a3tecnology.appmovie.data.model.movie.GenresResponse
import com.a3tecnology.appmovie.data.model.movie.MovieResponse

interface MovieRepository {

    suspend fun getGenres(): GenresResponse

     fun getMovieByGenrePagination(genreId: Int?): PagingSource<Int, MovieResponse>
     suspend fun getMovieByGenre(genreId: Int?): BasePaginationRemote<List<MovieResponse>>

     fun searchMovie(query: String?): PagingSource<Int, MovieResponse>

}
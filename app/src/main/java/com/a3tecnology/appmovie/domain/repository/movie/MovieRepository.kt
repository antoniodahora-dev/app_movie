package com.a3tecnology.appmovie.domain.repository.movie

import androidx.paging.PagingSource
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse

interface MovieRepository {

    suspend fun getGenres(): GenresResponse

     fun getMovieByGenre(genreId: Int?): PagingSource<Int, MovieResponse>

     fun searchMovie(query: String?): PagingSource<Int, MovieResponse>

}
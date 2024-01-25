package com.a3tecnology.appmovie.data.api

import com.a3tecnology.appmovie.data.model.BasePaginationRemote
import com.a3tecnology.appmovie.data.model.GenresResponse
import com.a3tecnology.appmovie.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ) : GenresResponse

    @GET("discover/movie")
    suspend fun getMovieByGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("with_genres") genreId: Int?
    ) : BasePaginationRemote<List<MovieResponse>>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("query") query: String?
    ) : BasePaginationRemote<List<MovieResponse>>
}
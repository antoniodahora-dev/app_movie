package com.a3tecnology.appmovie.domain.local.repository

import com.a3tecnology.appmovie.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {

    fun getMovies(): Flow<List<MovieEntity>>
    suspend fun insertMovies(movieEntity: MovieEntity)
    suspend fun deleteMovies(movieId: Int?)
}
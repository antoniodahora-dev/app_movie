package com.a3tecnology.appmovie.data.local.repository

import com.a3tecnology.appmovie.data.local.dao.MovieDao
import com.a3tecnology.appmovie.data.local.entity.MovieEntity
import com.a3tecnology.appmovie.domain.local.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
): MovieLocalRepository {
    override fun getMovies(): Flow<List<MovieEntity>> {
       return movieDao.getMovies()
    }

    override suspend fun insertMovies(movieEntity: MovieEntity) {
        movieDao.insertMovies(movieEntity)
    }

    override suspend fun deleteMovies(movieId: Int?) {
       movieDao.deleteMovies(movieId)
    }
}
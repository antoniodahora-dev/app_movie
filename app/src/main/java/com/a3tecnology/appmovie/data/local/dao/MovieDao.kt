package com.a3tecnology.appmovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a3tecnology.appmovie.data.local.entity.MovieEntity
import com.a3tecnology.appmovie.util.Columns
import com.a3tecnology.appmovie.util.Tables
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM ${Tables.MOVIE_TABLE}")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieEntity: MovieEntity)

    @Query("DELETE FROM ${Tables.MOVIE_TABLE} WHERE ${Columns.MOVIE_ID_COLUMN} = :movieId")
    suspend fun deleteMovies(movieId: Int?)
}
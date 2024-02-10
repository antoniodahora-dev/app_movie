package com.a3tecnology.appmovie.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a3tecnology.appmovie.data.local.dao.MovieDao
import com.a3tecnology.appmovie.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
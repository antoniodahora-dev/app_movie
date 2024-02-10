package com.a3tecnology.appmovie.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.a3tecnology.appmovie.data.local.dao.MovieDao
import com.a3tecnology.appmovie.data.local.db.AppDatabase
import com.a3tecnology.appmovie.util.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providerDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        Database.MOVIE_DATABASE

    ).build()

    @Provides
    fun providesMovieDao(database: AppDatabase): MovieDao = database.movieDao()
}
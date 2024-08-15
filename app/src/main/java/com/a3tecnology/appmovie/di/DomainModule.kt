package com.a3tecnology.appmovie.di

import com.a3tecnology.appmovie.data.local.repository.MovieLocalRepositoryImpl
import com.a3tecnology.appmovie.data.repository.auth.FirebaseAuthenticatorImpl
import com.a3tecnology.appmovie.data.repository.movie.MovieDetailsRepositoryImpl
import com.a3tecnology.appmovie.data.repository.movie.MovieRepositoryImpl
import com.a3tecnology.appmovie.data.repository.user.UserRepositoryImpl
import com.a3tecnology.appmovie.domain.local.repository.MovieLocalRepository
import com.a3tecnology.appmovie.domain.repository.auth.FirebaseAuthenticator
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import com.a3tecnology.appmovie.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticatorImpl(
        firebaseAuthenticatorImpl: FirebaseAuthenticatorImpl
    ) : FirebaseAuthenticator

    @Binds
    abstract fun bindsMovieRepositoryImpl(
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository

    @Binds
    abstract fun bindsMovieDetailsRepositoryImpl(
        movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl
    ) : MovieDetailsRepository

    @Binds
    abstract fun bindsMovieLocalRepositoryImpl(
        movieLocalRepositoryImpl: MovieLocalRepositoryImpl
    ) : MovieLocalRepository

    @Binds
    abstract fun bindsUserRepositoryImpl(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository


}
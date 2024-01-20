package com.a3tecnology.appmovie.di

import com.a3tecnology.appmovie.data.repository.auth.FirebaseAuthenticatorImpl
import com.a3tecnology.appmovie.domain.repository.auth.FirebaseAuthenticator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticatorImpl(
        bindsFirebaseAuthenticatorImpl: FirebaseAuthenticatorImpl
    ) : FirebaseAuthenticator
}
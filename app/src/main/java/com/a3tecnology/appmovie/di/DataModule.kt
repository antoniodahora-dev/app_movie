package com.a3tecnology.appmovie.di

import com.a3tecnology.appmovie.data.api.ServiceApi
import com.a3tecnology.appmovie.domain.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providersServiceProvider() = ServiceProvider()

    @Provides
    fun providerServiceApi(serviceProvider: ServiceProvider): ServiceApi {
        return serviceProvider.createService(ServiceApi::class.java)
    }
}
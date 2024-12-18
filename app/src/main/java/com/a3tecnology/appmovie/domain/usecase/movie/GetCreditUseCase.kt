package com.a3tecnology.appmovie.domain.usecase.movie

import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.movie.Credit
import com.a3tecnology.appmovie.domain.repository.movie.MovieDetailsRepository
import javax.inject.Inject

class GetCreditUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) {
    suspend operator fun invoke( movieId: Int?): Credit {
        return repository.getCredits(
           movieId = movieId
        ).toDomain()
    }
}
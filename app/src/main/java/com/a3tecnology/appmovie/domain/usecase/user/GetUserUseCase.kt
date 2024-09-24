package com.a3tecnology.appmovie.domain.usecase.user

import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.domain.repository.user.UserRepository
import javax.inject.Inject

//aula 367.112
class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return userRepository.getUser()
    }
}
package com.a3tecnology.appmovie.domain.usecase.user

import android.net.Uri
import com.a3tecnology.appmovie.domain.repository.user.UserRepository
import javax.inject.Inject

//aula 367.112
class SaveUserImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uri: Uri): String {
        return userRepository.saveUserImage(uri)
    }
}
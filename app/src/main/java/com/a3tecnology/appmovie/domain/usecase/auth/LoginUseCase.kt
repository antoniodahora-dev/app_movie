package com.a3tecnology.appmovie.domain.usecase.auth

import com.a3tecnology.appmovie.domain.repository.auth.FirebaseAuthenticator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator
) {
    suspend operator fun invoke(email: String, password: String) {
       firebaseAuthenticator.login(email, password)
    }
}
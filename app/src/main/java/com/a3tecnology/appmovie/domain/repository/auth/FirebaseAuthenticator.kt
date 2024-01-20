package com.a3tecnology.appmovie.domain.repository.auth

interface FirebaseAuthenticator {

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun forgot(email: String)
}
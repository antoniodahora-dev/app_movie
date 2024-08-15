package com.a3tecnology.appmovie.domain.repository.user

import com.a3tecnology.appmovie.domain.model.user.User

//aula 367.112
interface UserRepository {

    suspend fun update(user: User)

    suspend fun getUser(): User
}
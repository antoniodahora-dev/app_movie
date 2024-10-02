package com.a3tecnology.appmovie.domain.repository.user

import android.net.Uri
import com.a3tecnology.appmovie.domain.model.user.User

//aula 367.112
interface UserRepository {

    suspend fun update(user: User)

    //aula 374
    suspend fun saveUserImage(uri: Uri): String

    suspend fun getUser(): User
}
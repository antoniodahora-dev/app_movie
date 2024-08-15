package com.a3tecnology.appmovie.data.repository.user

import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.domain.repository.user.UserRepository
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//aula 367.112
class UserRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
): UserRepository {

    private val profileRef = firebaseDatabase.reference
        .child("profile")

    override suspend fun update(user: User) {
        return suspendCoroutine { continuation ->
            profileRef
                .child(FirebaseHelp.getUserId())
                .setValue(user)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    //aula 368.113
    override suspend fun getUser(): User {
        return suspendCoroutine { continuation ->
            profileRef
                .child(FirebaseHelp.getUserId())
                .get() // aula 368.113
                 .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result?.getValue((User::class.java))
                        if (user != null) {
                            continuation.resumeWith(Result.success(user))
                        } else {
                            continuation.resumeWithException(Exception("Usuário não encontrado!"))
                        }
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}
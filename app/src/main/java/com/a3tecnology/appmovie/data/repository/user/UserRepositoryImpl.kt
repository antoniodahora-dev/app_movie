package com.a3tecnology.appmovie.data.repository.user

import android.net.Uri
import android.util.Log
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.domain.repository.user.UserRepository
import com.a3tecnology.appmovie.util.FirebaseHelp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//aula 367.112
class UserRepositoryImpl @Inject constructor(
   firebaseDatabase: FirebaseDatabase,
   firebaseStorage: FirebaseStorage
): UserRepository {

    private val profileRef = firebaseDatabase.reference
        .child("profile")

    //Aula 374
    private val profileImageRef = firebaseStorage.reference
        .child("images")
        .child("profiles")
        .child(FirebaseHelp.getUserId())
        .child("image_profile.jpeg")

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

    // aula 374
    override suspend fun saveUserImage(uri: Uri): String {

       return suspendCoroutine { continuation ->

           val uploadTask = profileImageRef.putFile(uri)
           uploadTask.addOnProgressListener { taskSnapshot ->

          val progress = (100 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
               Log.d("INFOTEST", "Upload is ${progress.toInt()} % done")

           }.addOnSuccessListener {

               profileImageRef.downloadUrl.addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       val downloadUri = task.result.toString()
                       continuation.resumeWith(Result.success(downloadUri))
                   } else {
                       task.exception?.let {
                           continuation.resumeWith(Result.failure(it))
                       }
                   }
               }

           }.addOnFailureListener {
               continuation.resumeWithException(it)
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
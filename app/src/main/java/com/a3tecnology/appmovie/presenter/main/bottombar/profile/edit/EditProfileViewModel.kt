package com.a3tecnology.appmovie.presenter.main.bottombar.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.domain.usecase.user.GetUserUseCase
import com.a3tecnology.appmovie.domain.usecase.user.UserUpdateUseCase
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

//aula 367.112
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userUpdateUseCase: UserUpdateUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    fun update(user: User) = liveData(context = Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            userUpdateUseCase(user)

            delay(4000)
            emit(StateView.Success(Unit))

        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

    fun getUser() = liveData(context = Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val user = getUserUseCase()

            delay(4000)
            emit(StateView.Success(user))

        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }
}
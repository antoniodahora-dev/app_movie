package com.a3tecnology.appmovie.presenter.main.bottombar.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.domain.usecase.user.GetUserUseCase
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

// AUlA 375
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    ) : ViewModel() {

    fun getUser() = liveData(context = Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val user = getUserUseCase()

//            delay(4000)
            emit(StateView.Success(user))

        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }
}
package com.a3tecnology.appmovie.presenter.main.bottombar.profile.edit

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.R
import com.a3tecnology.appmovie.domain.model.user.User
import com.a3tecnology.appmovie.domain.usecase.user.GetUserUseCase
import com.a3tecnology.appmovie.domain.usecase.user.SaveUserImageUseCase
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
    private val getUserUseCase: GetUserUseCase,
    private val saveUserImageUseCase: SaveUserImageUseCase
): ViewModel() {

    private val _validateData = MutableLiveData<Pair<Boolean, Int?>>()
    val validateData: MutableLiveData<Pair<Boolean, Int?>> = _validateData

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

    // Aula 373
    fun validateData(
        name: String,
        surName: String,
        phone: String,
        genre: String,
        country: String
    ) {
        if (name.isEmpty()) {
//            showSnackBar(message = R.string.txt_name_empty_edit_profile_fragment)
            validateData.value = Pair(false, R.string.txt_name_empty_edit_profile_fragment)
            return
        }

        if (surName.isEmpty()) {
//            showSnackBar(message = R.string.txt_surnane_empty_edit_profile_fragment)
            validateData.value = Pair(false, R.string.txt_surnane_empty_edit_profile_fragment)
            return
        }

        if (phone.isEmpty()) {
//            showSnackBar(message = R.string.txt_phone_invalid_edit_profile_fragment)
            validateData.value = Pair(false, R.string.txt_phone_invalid_edit_profile_fragment)
            return
        }

       if (genre.isEmpty()) {
//            showSnackBar(message = R.string.txt_genre_empty_edit_profile_fragment)
         validateData.value = Pair(false, R.string.txt_genreEdit_empty_profile_fragment)
            return
        }

        if (country.isEmpty()) {
//            showSnackBar(message = R.string.txt_country_empty_edit_profile_fragment)
            validateData.value = Pair(false, R.string.txt_country_empty_edit_profile_fragment)
            return
        }

        validateData.value = Pair(true, null)

    }

    // Aula 374
    fun saveUserImage(uri: Uri) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val url = saveUserImageUseCase(uri)

//            delay(4000)
            emit(StateView.Success(url))

        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(StateView.Error(message = exception.message))
        }
    }

}


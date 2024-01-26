package com.a3tecnology.appmovie.presenter.main.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieByGenreUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieDetailsUseCase
import com.a3tecnology.appmovie.util.Constants
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCas: GetMovieDetailsUseCase
) : ViewModel() {

    fun getMovieDetails(movieId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movieDetails = getMovieDetailsUseCas.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE,
                movieId = movieId
            )
            emit(StateView.Success(movieDetails))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }

    }
}
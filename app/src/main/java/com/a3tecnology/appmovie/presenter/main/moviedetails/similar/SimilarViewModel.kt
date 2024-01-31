package com.a3tecnology.appmovie.presenter.main.moviedetails.similar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.usecase.movie.GetSimilarUseCase
import com.a3tecnology.appmovie.util.Constants.Movie
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val getSimilarUseCase: GetSimilarUseCase
) : ViewModel() {

    fun getSimilar(movieId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movies = getSimilarUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Movie.LANGUAGE,
                movieId = movieId
            )

            emit(StateView.Success(movies))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}
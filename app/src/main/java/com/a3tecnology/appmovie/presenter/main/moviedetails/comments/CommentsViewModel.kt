package com.a3tecnology.appmovie.presenter.main.moviedetails.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieReviewsUseCase
import com.a3tecnology.appmovie.util.Constants
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    fun getMovieReviews(movieId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movieReviewsDetails = getMovieReviewsUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Constants.Movie.LANGUAGE_ENGLISH,
                movieId = movieId
            )
            emit(StateView.Success(movieReviewsDetails))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }

    }


}
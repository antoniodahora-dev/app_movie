package com.a3tecnology.appmovie.presenter.main.moviedetails.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.local.usecase.InsertMoviesUseCase
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.usecase.movie.GetCreditUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieDetailsUseCase
import com.a3tecnology.appmovie.util.Constants
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCas: GetMovieDetailsUseCase,
    private val getCreditUseCase: GetCreditUseCase,
    private val insertMoviesUseCase: InsertMoviesUseCase
) : ViewModel() {

    private val _movieId = MutableLiveData(0)
    val movieId: LiveData<Int> = _movieId

    fun getMovieDetails(movieId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movieDetails = getMovieDetailsUseCas.invoke(
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

    fun getCredit(movieId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movieCredit = getCreditUseCase(
                movieId = movieId
            )
            emit(StateView.Success(movieCredit))

        } catch (e: HttpException) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }

    }

    fun insertMovie(movie: Movie) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            insertMoviesUseCase(movie)
            emit(StateView.Success(Unit))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    fun setMovieId(movieId: Int) {
        _movieId.value = movieId
    }

}
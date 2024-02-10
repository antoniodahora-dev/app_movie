package com.a3tecnology.appmovie.presenter.main.bottombar.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.data.mapper.toPresentation
import com.a3tecnology.appmovie.domain.usecase.movie.GetGenresUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieByGenreUseCase
import com.a3tecnology.appmovie.util.Constants.Movie
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase
) : ViewModel() {

    fun getGenres() = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val genres = getGenresUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Movie.LANGUAGE
            ).map { it.toPresentation() }

            emit(StateView.Success(genres))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }

    fun getMovieByGenre(genreId: Int?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val movies = getMovieByGenreUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Movie.LANGUAGE,
                genreId = genreId
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
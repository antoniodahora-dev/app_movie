package com.a3tecnology.appmovie.presenter.main.bottombar.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.usecase.movie.SearchMovieUseCase
import com.a3tecnology.appmovie.util.Constants.Movie
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    fun searchMovie(query: String?) = liveData(Dispatchers.IO) {

        try {
            emit(StateView.Loading())

            val search = searchMovieUseCase.invoke(
                apiKey = BuildConfig.API_KEY,
                language = Movie.LANGUAGE,
                query = query
            )

            emit(StateView.Success(search))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}
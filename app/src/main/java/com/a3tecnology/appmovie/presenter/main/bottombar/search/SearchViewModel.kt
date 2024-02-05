package com.a3tecnology.appmovie.presenter.main.bottombar.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.usecase.movie.SearchMovieUseCase
import com.a3tecnology.appmovie.util.Constants
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _movieList =  MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList


    private val _searchState = MutableLiveData<StateView<Unit>>()
    val searchState: LiveData<StateView<Unit>>
        get() = _searchState


    fun searchMovie(query: String?)  {

        viewModelScope.launch {
            try {
                _searchState.postValue(StateView.Loading())

                val search = searchMovieUseCase(
                    apiKey = BuildConfig.API_KEY,
                    language = Constants.Movie.LANGUAGE,
                    query = query
                )

                _movieList.postValue(search)
                _searchState.postValue(StateView.Success(Unit))

            } catch (e: Exception) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))

            } catch (e: Exception) {
                e.printStackTrace()
                _searchState.postValue(StateView.Error(message = e.message))
            }
        }

    }
}
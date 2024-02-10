package com.a3tecnology.appmovie.presenter.main.bottombar.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a3tecnology.appmovie.domain.local.usecase.GetMoviesUseCase
import com.a3tecnology.appmovie.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    private val _movieList = MutableLiveData(mutableListOf<Movie>())
    var movieList: LiveData<MutableList<Movie>> = _movieList


     fun getMovie() = viewModelScope.launch {
        getMoviesUseCase().collect { movies ->

            _movieList.postValue(movies.toMutableList())
        }

    }
}
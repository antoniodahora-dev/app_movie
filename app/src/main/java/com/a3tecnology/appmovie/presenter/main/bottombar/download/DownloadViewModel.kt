package com.a3tecnology.appmovie.presenter.main.bottombar.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a3tecnology.appmovie.domain.local.usecase.DeleteMovieUseCase
import com.a3tecnology.appmovie.domain.local.usecase.GetMoviesUseCase
import com.a3tecnology.appmovie.domain.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val searchMovieUseCase: DeleteMovieUseCase
): ViewModel() {

    private val _movieList = MutableLiveData(mutableListOf<Movie>())
    var movieList: LiveData<MutableList<Movie>> = _movieList

    private val _movieSearchList = MutableLiveData(mutableListOf<Movie>())
    var movieSearchList: LiveData<MutableList<Movie>> = _movieSearchList


     fun getMovie() = viewModelScope.launch {
        getMoviesUseCase().collect { movies ->

            _movieList.postValue(movies.toMutableList())
        }
    }

    fun deleteMovie(movieId: Int?) = viewModelScope.launch {

        deleteMovieUseCase(movieId)

        val newList = _movieList.value?.apply {
            removeIf { it.id == movieId }
        }

        _movieList.postValue(newList)
    }

    fun searchMovie(search: String) = viewModelScope.launch {

       val newList = _movieList.value?.filter { it.title?.contains(search, true) == true }
       _movieSearchList.postValue(newList?.toMutableList())
    }



}
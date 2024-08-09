package com.a3tecnology.appmovie.presenter.main.bottombar.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.usecase.movie.GetGenresUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieByGenrePaginationUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieByGenreUseCase
import com.a3tecnology.appmovie.presenter.model.MoviesByGenre
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase
) : ViewModel() {

    private val _movieList = MutableLiveData<List<MoviesByGenre>>()
    val movieList:  LiveData<List<MoviesByGenre>>
        get() = _movieList

    private val _homeState = MutableLiveData<StateView<Unit>>()
    val homeState: LiveData<StateView<Unit>> get() = _homeState

    init {
        getGenres()
    }

    //aula 360
    fun getGenres() {
        viewModelScope.launch {
            try {
                _homeState.postValue(StateView.Loading())
                val genres = getGenresUseCase()
                getMovieByGenre(genres)

            } catch (e: Exception) {
                e.printStackTrace()
                _homeState.postValue(StateView.Error(e.message))
            }
        }
    }

    private fun getMovieByGenre( genres: List<Genre>) {

        val moviesByGenre: MutableList<MoviesByGenre> = mutableListOf()

        viewModelScope.launch {
            genres.forEach { genre ->
                try {
                    val movies = getMovieByGenreUseCase(genreId = genre.id)
                    val movieByGenre = MoviesByGenre(
                        id = genre.id,
                        name = genre.name,
                        movies = movies.map { it.toDomain()  }.take(5)
                    )
                    moviesByGenre.add(movieByGenre)
                    if (moviesByGenre.size == genres.size) {
                      _movieList.postValue(moviesByGenre)
                        _homeState.postValue(StateView.Success(Unit))
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    _homeState.postValue(StateView.Error(e.message))
                }
            }
            }
        }
    }

//    fun getMovieByGenre(genreId: Int?) = liveData(Dispatchers.IO) {
//
//        try {
//            emit(StateView.Loading())
//
//            val movies = getMovieByGenreUseCase.invoke(
//                genreId = genreId
//            )
//
//            emit(StateView.Success(movies.map { it.toDomain() }))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        }
//    }




//    fun getGenres() = liveData(Dispatchers.IO) {
//
//        try {
//            emit(StateView.Loading())
//
//            val genres = getGenresUseCase.invoke(
//            ).map { it.toPresentation() }
//
//            emit(StateView.Success(genres))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emit(StateView.Error(message = e.message))
//        }
//    }

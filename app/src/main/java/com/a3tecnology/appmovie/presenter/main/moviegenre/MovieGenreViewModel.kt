package com.a3tecnology.appmovie.presenter.main.moviegenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.a3tecnology.appmovie.domain.model.movie.Movie
import com.a3tecnology.appmovie.domain.usecase.movie.GetMovieByGenrePaginationUseCase
import com.a3tecnology.appmovie.domain.usecase.movie.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(
    private val getMovieByGenrePaginationUseCase: GetMovieByGenrePaginationUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _movieList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movieList get() = _movieList.asStateFlow()

    private var currentGenreId: Int? = null

    fun getMovieByGenrePagination(genreId: Int?, forceRequest: Boolean) = viewModelScope.launch {
        if (genreId != currentGenreId || forceRequest) {

            currentGenreId = genreId

            getMovieByGenrePaginationUseCase(
                genreId = genreId
            ).cachedIn(viewModelScope).collectLatest {
                _movieList.emit(it)
            }
        }

    }


    fun searchMovie(query: String?) : Flow<PagingData<Movie>> {
       return searchMovieUseCase(
                query = query
            ).cachedIn(viewModelScope)
        }


//    fun getMovieByGenre2(genreId: Int?) = liveData(Dispatchers.IO) {
//
//        try {
//            emit(StateView.Loading())
//
//            val movies = getMovieByGenreUseCase.invoke(
//                apiKey = BuildConfig.API_KEY,
//                language = Movie.LANGUAGE,
//                genreId = genreId
//            )
//
//            emit(StateView.Success(movies))
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

//    fun searchMovie(query: String?) = liveData(Dispatchers.IO) {
//
//        try {
//            emit(StateView.Loading())
//
//            val search = searchMovieUseCase.invoke(
//                apiKey = BuildConfig.API_KEY,
//                language = Constants.Movie.LANGUAGE,
//                query = query
//            )
//
//            emit(StateView.Success(search))
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
}
package com.a3tecnology.appmovie.presenter.main.bottombar.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.a3tecnology.appmovie.BuildConfig
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.usecase.movie.SearchMovieUseCase
import com.a3tecnology.appmovie.util.Constants
import com.a3tecnology.appmovie.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
): ViewModel() {
    fun searchMovie(query: String?) : Flow<PagingData<Movie>> {
        return searchMovieUseCase(
            query = query
        ).cachedIn(viewModelScope)
    }

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
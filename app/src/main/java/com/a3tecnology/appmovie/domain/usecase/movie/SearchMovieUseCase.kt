package com.a3tecnology.appmovie.domain.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.Genre
import com.a3tecnology.appmovie.domain.model.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import com.a3tecnology.appmovie.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
//    suspend operator fun invoke(apiKey: String, language: String?, query: String?): List<Movie> {
//        return repository.searchMovie(
//            apiKey = apiKey,
//            language = language,
//            query = query
//        ).filter { it.backdropPath != null}.map {  it.toDomain() }
//    }

    operator fun invoke(
        query: String?
    ): Flow<PagingData<Movie>> = Pager(

        config = PagingConfig(
            pageSize = Constants.Paging.NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = Constants.Paging.DEFAULT_PAGE_INDEX
        ),
        pagingSourceFactory = {
            repository.searchMovie(
                query = query
            )
        }
    ).flow.map { pagingData ->
        pagingData.map { movieResponse ->
            movieResponse.toDomain()
        }
    }
}
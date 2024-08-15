package com.a3tecnology.appmovie.domain.usecase.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.a3tecnology.appmovie.data.mapper.toDomain
import com.a3tecnology.appmovie.domain.model.movie.Movie
import com.a3tecnology.appmovie.domain.repository.movie.MovieRepository
import com.a3tecnology.appmovie.util.Constants.Paging.DEFAULT_PAGE_INDEX
import com.a3tecnology.appmovie.util.Constants.Paging.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieByGenrePaginationUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(
        genreId: Int?
    ): Flow<PagingData<Movie>> = Pager(

        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = DEFAULT_PAGE_INDEX
        ),
        pagingSourceFactory = {
            repository.getMovieByGenrePagination(
            genreId = genreId
        )
        }
    ).flow.map { pagingData ->
        pagingData.map { movieResponse ->
            movieResponse.toDomain()
        }
    }

}
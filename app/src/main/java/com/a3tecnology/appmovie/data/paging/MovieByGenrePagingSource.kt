package com.a3tecnology.appmovie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.a3tecnology.appmovie.data.api.ServiceApi
import com.a3tecnology.appmovie.data.model.movie.MovieResponse
import com.a3tecnology.appmovie.util.Constants.Paging.DEFAULT_PAGE_INDEX

//Aula 348

class MovieByGenrePagingSource(
   private val serviceApi: ServiceApi,
   private val genreId: Int?
) : PagingSource<Int, MovieResponse>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieResponse> {
        return try {

            val page = params.key ?: DEFAULT_PAGE_INDEX
            val result = serviceApi.getMovieByGenrePagination(
                genreId = genreId,
                page = page
            ).results ?: emptyList()
            LoadResult.Page(
                data = result,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
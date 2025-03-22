package com.jrprofessor.platformcommons.ui.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jrprofessor.platformcommons.network.ApiInterface
import com.jrprofessor.platformcommons.network.MovieInterface
import com.jrprofessor.platformcommons.response.Movie
import com.jrprofessor.platformcommons.response.User
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: MovieInterface
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getMovieList(page=page)

            if (response.isSuccessful) {
                val movieData = response.body()?.movie ?: emptyList()
                LoadResult.Page(
                    data = movieData,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movieData.isNotEmpty()) page + 1 else null
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
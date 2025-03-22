package com.jrprofessor.platformcommons.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jrprofessor.platformcommons.network.ApiUserService
import com.jrprofessor.platformcommons.response.User
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val apiService: ApiUserService
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getUserData(page)

            if (response.isSuccessful) {
                val userData = response.body()?.user ?: emptyList()
                LoadResult.Page(
                    data = userData,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (userData.isNotEmpty()) page + 1 else null
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
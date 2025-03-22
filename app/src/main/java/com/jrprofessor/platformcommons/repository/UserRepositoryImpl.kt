package com.jrprofessor.platformcommons.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.jrprofessor.platformcommons.ui.main.UserPagingSource
import com.jrprofessor.platformcommons.network.ApiInterface
import com.jrprofessor.platformcommons.network.MovieInterface
import com.jrprofessor.platformcommons.network.Resource
import com.jrprofessor.platformcommons.response.Movie
import com.jrprofessor.platformcommons.response.MovieDetailsResponse
import com.jrprofessor.platformcommons.response.MovieListResponse
import com.jrprofessor.platformcommons.response.User
import com.jrprofessor.platformcommons.response.UserResponse
import com.jrprofessor.platformcommons.response.UserSaveResponse
import com.jrprofessor.platformcommons.ui.movie.MoviePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: ApiInterface,
    private val movieApi: MovieInterface,
    private val gson: Gson,
) : UserRepository {
    override suspend fun getUser(page: Int): Resource<UserResponse> {
        return try {
            val response = userApi.getUserData(page)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.toString())
            }
        } catch (throwable: Throwable) {
            Resource.error(throwable.message)
        }
    }

    override suspend fun getMovieList(page: Int): Resource<MovieListResponse> {
        return try {
            val response = movieApi.getMovieList(page = page)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.toString())
            }
        } catch (throwable: Throwable) {
            Resource.error(throwable.message)
        }
    }
    override suspend fun getMovieDetail(movieId: Int): Resource<MovieDetailsResponse> {
        return try {
            val response = movieApi.getMovieDetail(movieId = movieId)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.toString())
            }
        } catch (throwable: Throwable) {
            Resource.error(throwable.message)
        }
    }

    override suspend fun saveUserData(body: HashMap<String, String>): Resource<UserSaveResponse> {
        return try {
            val response = userApi.saveUserData(body)
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(response.toString())
            }
        } catch (throwable: Throwable) {
            Resource.error(throwable.message)
        }

    }

    override fun getUsers(): LiveData<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(userApi) }
        ).liveData
    }
    override fun getMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi) }
        ).liveData
    }
}
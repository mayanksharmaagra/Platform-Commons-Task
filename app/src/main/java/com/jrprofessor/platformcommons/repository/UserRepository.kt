package com.jrprofessor.platformcommons.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jrprofessor.platformcommons.network.Resource
import com.jrprofessor.platformcommons.response.Movie
import com.jrprofessor.platformcommons.response.MovieDetailsResponse
import com.jrprofessor.platformcommons.response.MovieListResponse
import com.jrprofessor.platformcommons.response.User
import com.jrprofessor.platformcommons.response.UserResponse
import com.jrprofessor.platformcommons.response.UserSaveResponse
import com.jrprofessor.platformcommons.roomdb.UserEntity
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface UserRepository {
    fun getUsers(): LiveData<PagingData<User>>
    fun getMovies(): LiveData<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): Resource<MovieDetailsResponse>
    suspend fun saveUserData(hashMap: HashMap<String, String>): Resource<UserSaveResponse>
}
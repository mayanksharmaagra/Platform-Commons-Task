package com.jrprofessor.platformcommons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.PagingData
import com.jrprofessor.platformcommons.network.Resource
import com.jrprofessor.platformcommons.repository.DatabaseRepository
import com.jrprofessor.platformcommons.repository.UserRepository
import com.jrprofessor.platformcommons.response.Movie
import com.jrprofessor.platformcommons.response.MovieDetailsResponse
import com.jrprofessor.platformcommons.response.MovieListResponse
import com.jrprofessor.platformcommons.response.User
import com.jrprofessor.platformcommons.response.UserResponse
import com.jrprofessor.platformcommons.response.UserSaveResponse
import com.jrprofessor.platformcommons.roomdb.UserEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val databaseRepository: DatabaseRepository
) : BaseViewModel() {
    private val userListObserver by lazy { MutableLiveData<Resource<UserResponse>>() }
    fun getUserListObserver(): LiveData<Resource<UserResponse>> = userListObserver

    private val movieListObserver by lazy { MutableLiveData<Resource<MovieListResponse>>() }
    fun getMovieListObserver(): LiveData<Resource<MovieListResponse>> = movieListObserver

    private val userSaveObserver by lazy { MutableLiveData<Resource<UserSaveResponse>>() }
    fun getUserSaveObserver(): LiveData<Resource<UserSaveResponse>> = userSaveObserver

    private val movieDetailObserver by lazy { MutableLiveData<Resource<MovieDetailsResponse>>() }
    fun getMovieDetailObserver(): LiveData<Resource<MovieDetailsResponse>> = movieDetailObserver

    fun getUsers(page: Int) {
        launch {
            userListObserver.value = Resource.loading()
            userListObserver.value = userRepository.getUser(page)
        }
    }

    fun getMovieList(page: Int) {
        launch {
            movieListObserver.value = Resource.loading()
            movieListObserver.value = userRepository.getMovieList(page)
        }
    }

    fun getMovieDetail(movieId: Int) {
        launch {
            movieDetailObserver.value = Resource.loading()
            movieDetailObserver.value = userRepository.getMovieDetail(movieId)
        }
    }

    fun saveUser(body: HashMap<String, String>) {
        launch {
            userSaveObserver.value = Resource.loading()
            userSaveObserver.value = userRepository.saveUserData(body)
        }
    }

    fun saveLocalDB(userDao: UserEntity) {
        launch {
            databaseRepository.insertData(userDao)
        }
    }

    fun getAllUnSyncUser(): List<UserEntity> = databaseRepository.getAllUnsyncedUsers()
    fun getAllUser(): LiveData<List<UserEntity>> = databaseRepository.getAllUser()


    val userList: LiveData<PagingData<User>> = userRepository.getUsers()
    val movieList: LiveData<PagingData<Movie>> = userRepository.getMovies()
}
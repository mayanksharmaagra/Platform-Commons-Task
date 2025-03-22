package com.jrprofessor.platformcommons.network

import com.jrprofessor.platformcommons.response.MovieListResponse
import com.jrprofessor.platformcommons.response.UserResponse
import com.jrprofessor.platformcommons.response.UserSaveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): UserResponse

    @GET("api/users")
    suspend fun getUserData(@Query("page") page: Int): Response<UserResponse>

    @POST("api/users")
    suspend fun saveUserData(@Body body: HashMap<String, String>): Response<UserSaveResponse>
}
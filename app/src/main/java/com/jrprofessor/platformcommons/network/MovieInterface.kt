package com.jrprofessor.platformcommons.network

import com.jrprofessor.platformcommons.response.MovieDetailsResponse
import com.jrprofessor.platformcommons.response.MovieListResponse
import com.jrprofessor.platformcommons.response.UserResponse
import com.jrprofessor.platformcommons.response.UserSaveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("3/trending/movie/day")
    suspend fun getMovieList(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = "3443fbf69e2908725c60fcf83404fe46"
    ): Response<MovieListResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "3443fbf69e2908725c60fcf83404fe46"
    ): Response<MovieDetailsResponse>
}
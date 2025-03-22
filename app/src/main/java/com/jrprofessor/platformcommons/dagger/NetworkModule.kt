package com.jrprofessor.platformcommons.dagger

import android.content.Context
import com.google.gson.Gson
import com.jrprofessor.platformcommons.network.ApiInterface
import com.jrprofessor.platformcommons.network.MovieInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(
    private val serverUserBaseUrl: String,
    private val serverMovieBaseUrl: String,
    private val debug: Boolean
) {

    @Provides
    @Singleton
    @MovieRetrofit
    fun provideMovieRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(serverMovieBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @UserRetrofit
    fun provideUserRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(serverUserBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    @Singleton
    fun provideUserApi(@UserRetrofit  retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApi(@MovieRetrofit retrofit: Retrofit): MovieInterface {
        return retrofit.create(MovieInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (debug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return HttpLoggingInterceptor().setLevel(level)
    }


}

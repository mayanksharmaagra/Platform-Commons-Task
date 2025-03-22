package com.jrprofessor.platformcommons.dagger

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.jrprofessor.platformcommons.repository.DatabaseRepository
import com.jrprofessor.platformcommons.repository.DatabaseRepositoryImpl
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.repository.UserRepository
import com.jrprofessor.platformcommons.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {
    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository {
        return databaseRepositoryImpl
    }
    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: ProjectApplication): SharedPreferences {
        return context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}
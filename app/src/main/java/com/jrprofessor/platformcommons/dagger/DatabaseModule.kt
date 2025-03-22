package com.jrprofessor.platformcommons.dagger

import android.content.Context
import androidx.room.Room
import com.jrprofessor.platformcommons.roomdb.DatabaseBuilder
import com.jrprofessor.platformcommons.roomdb.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): DatabaseBuilder {
        return Room.databaseBuilder(context, DatabaseBuilder::class.java, "user_data").build()
    }

    @Provides
    fun providePassbookDao(database: DatabaseBuilder): UserDao {
        return database.getDatabase()
    }
}
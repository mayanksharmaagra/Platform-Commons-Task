package com.jrprofessor.platformcommons.dagger

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jrprofessor.platformcommons.ProjectApplication
import com.jrprofessor.platformcommons.roomdb.DatabaseBuilder
import com.jrprofessor.platformcommons.roomdb.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @JvmStatic
    internal fun provideContext(application: ProjectApplication): Context =
        application.applicationContext

    @Provides
    @JvmStatic
    internal fun provideApplication(application: ProjectApplication): Application = application
}
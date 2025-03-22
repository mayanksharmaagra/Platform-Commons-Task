package com.jrprofessor.platformcommons.dagger

import androidx.work.WorkerFactory
import com.jrprofessor.platformcommons.CustomWorkFactory
import dagger.Binds
import dagger.Module

@Module
abstract class WorkerModule {

    @Binds
    abstract fun bindWorkerFactory(factory: CustomWorkFactory): WorkerFactory
}
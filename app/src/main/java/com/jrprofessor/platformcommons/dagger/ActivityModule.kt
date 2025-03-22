package com.jrprofessor.platformcommons.dagger

import com.jrprofessor.platformcommons.ui.movie.MovieListActivity
import com.jrprofessor.platformcommons.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun movieListActivity(): MovieListActivity
}
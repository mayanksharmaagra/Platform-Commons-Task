package com.jrprofessor.platformcommons.dagger

import com.jrprofessor.platformcommons.ui.main.HomeFragment
import com.jrprofessor.platformcommons.ui.main.UserAddFragment
import com.jrprofessor.platformcommons.ui.movie.MovieDetailFragment
import com.jrprofessor.platformcommons.ui.movie.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun userAddFragment(): UserAddFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun movieListFragment(): MovieListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun movieDetailFragment(): MovieDetailFragment
}
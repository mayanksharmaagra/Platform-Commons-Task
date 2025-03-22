package com.jrprofessor.platformcommons.dagger

import com.jrprofessor.platformcommons.ProjectApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RepositoryModule::class,
        AppModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        ActivityModule::class,
        FragmentBindingModule::class,
        WorkerModule::class,
    ]
)
interface AppComponent : AndroidInjector<ProjectApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: ProjectApplication): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }
}
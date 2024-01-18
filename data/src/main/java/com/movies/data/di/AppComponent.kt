package com.movies.data.di

import android.content.Context
import com.movies.DataProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PublicModule::class])
@Singleton
internal interface AppComponent {

    fun inject(provider: DataProvider)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}
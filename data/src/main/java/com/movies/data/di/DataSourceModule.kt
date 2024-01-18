package com.movies.data.di

import com.movies.DataSource
import com.movies.DataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Dagger providers for DataSource components
 */
@Module(includes = [DatabaseModule::class])
internal abstract class DataSourceModule {
    /**
     * provides datasource
     */
    @Binds
    @Singleton
    abstract fun provideDataSource(impl: DataSourceImpl): DataSource
}
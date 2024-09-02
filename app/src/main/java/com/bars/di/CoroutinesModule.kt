package com.bars.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UseCaseDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @Provides
    @UseCaseDispatcher
    fun provideUseCaseDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @DispatcherIO
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @ApplicationScope
    fun provideCoroutineScope() = CoroutineScope(
        SupervisorJob() + CoroutineExceptionHandler { _, exception ->
            println(exception)
        }
    )
}

package com.shoshin.rickandmorty.di.dispatchers

import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import com.shoshin.rickandmorty.di.dispatchers.CoroutinesDispatchersWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DispatchersModule {
    @Provides
    fun provideDispatchers(): DispatchersWrapper = CoroutinesDispatchersWrapper()
}
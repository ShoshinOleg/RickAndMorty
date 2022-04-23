package com.shoshin.rickandmorty.di.characters

import com.shoshin.data.interfaces.sources.ICharactersLocalSource
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.repositories.CharactersRepository
import com.shoshin.data.sources.CharactersLocalSource
import com.shoshin.data.sources.CharactersRemoteSource
import com.shoshin.domain.repositories.ICharactersRepository
import com.shoshin.domain.usecases.implementations.GetCharacterUseCase
import com.shoshin.domain.usecases.implementations.GetCharactersUseCase
import com.shoshin.domain.usecases.interfaces.IGetCharacterUseCase
import com.shoshin.domain.usecases.interfaces.IGetCharactersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface CharactersBindModule {
    @Binds
    fun bindCharactersRepository(repo: CharactersRepository): ICharactersRepository
    @Binds
    fun bindCharacterRemoteSource(source: CharactersRemoteSource): ICharactersRemoteSource
    @Binds
    fun bindCharacterLocalSource(source: CharactersLocalSource): ICharactersLocalSource
    @Binds
    fun bindGetCharactersUseCase(useCase: GetCharactersUseCase): IGetCharactersUseCase
    @Binds
    fun bindGetCharacterUseCase(useCase: GetCharacterUseCase): IGetCharacterUseCase
}
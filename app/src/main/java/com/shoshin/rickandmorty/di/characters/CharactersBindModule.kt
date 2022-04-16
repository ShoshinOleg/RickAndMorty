package com.shoshin.rickandmorty.di.characters

import com.shoshin.data.entities.CharacterRemote
import com.shoshin.data.entities.CharacterRemoteToCharacterDomainMapper
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.repositories.CharactersRepository
import com.shoshin.data.responses.CharactersResponse
import com.shoshin.data.responses.CharactersResponseToCharactersRemoteMapper
import com.shoshin.data.sources.CharactersRemoteSource
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
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
}
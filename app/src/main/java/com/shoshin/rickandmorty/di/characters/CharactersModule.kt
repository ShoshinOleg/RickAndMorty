package com.shoshin.rickandmorty.di.characters

import com.shoshin.data.entities.CharacterRemote
import com.shoshin.data.entities.CharacterRemoteToCharacterDomainMapper
import com.shoshin.data.responses.CharactersResponse
import com.shoshin.data.responses.CharactersResponseToCharactersRemoteMapper
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.entities.CharacterDomain
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CharactersModule {
    @Provides
    fun provideResponseToCharactersRemoteMapper(): Mapper<CharactersResponse, List<CharacterRemote>> =
        CharactersResponseToCharactersRemoteMapper()
    @Provides
    fun bindCharacterRemoteToCharacterDomainMapper(): Mapper<CharacterRemote, CharacterDomain> =
        CharacterRemoteToCharacterDomainMapper()
}
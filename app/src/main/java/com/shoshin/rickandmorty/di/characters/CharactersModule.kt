package com.shoshin.rickandmorty.di.characters

import com.shoshin.data.db.AppDatabase
import com.shoshin.data.db.CharacterRemoteKeysDao
import com.shoshin.data.db.CharactersDao
import com.shoshin.data.db.entities.CharacterDataToDbMapper
import com.shoshin.data.db.entities.CharacterDbToDataMapper
import com.shoshin.data.db.entities.CharacterDbo
import com.shoshin.data.entities.CharacterData
import com.shoshin.data.entities.CharacterDataToDomainMapper
import com.shoshin.data.remote.entities.CharacterRemote
import com.shoshin.data.remote.entities.CharacterRemoteToDataMapper
import com.shoshin.data.responses.CharactersResponse
import com.shoshin.data.responses.CharactersResponseToCharactersRemoteMapper
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.entities.CharacterDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CharactersModule {
    @Provides
    fun provideCharactersDao(db: AppDatabase): CharactersDao = db.charactersDao()

    @Provides
    fun provideCharactersKeysDao(db: AppDatabase): CharacterRemoteKeysDao = db.remoteKeysDao()

    @Provides
    fun provideResponseToCharactersRemoteMapper(): Mapper<CharactersResponse, List<CharacterRemote>> =
        CharactersResponseToCharactersRemoteMapper()

    @Provides
    fun provideRemoteToDataMapper(): Mapper<CharacterRemote, CharacterData> =
        CharacterRemoteToDataMapper()

    @Provides
    fun provideDataToDbMapper(): Mapper<CharacterData, CharacterDbo> =
        CharacterDataToDbMapper()

    @Provides
    fun provideDbToDataMapper(): Mapper<CharacterDbo, CharacterData> = CharacterDbToDataMapper()

    @Provides
    fun provideDataToDomainMapper(): Mapper<CharacterData, CharacterDomain> =
        CharacterDataToDomainMapper()
}
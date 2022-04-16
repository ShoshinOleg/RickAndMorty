package com.shoshin.data.sources

import com.shoshin.data.entities.CharacterRemote
import com.shoshin.data.entities.CharacterRemoteToCharacterDomainMapper
import com.shoshin.data.interfaces.services.ICharactersService
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.responses.CharactersResponse
import com.shoshin.data.responses.CharactersResponseToCharactersRemoteMapper
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import com.shoshin.domain.entities.CharacterDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRemoteSource @Inject constructor(
    private val remoteService: ICharactersService,
    private val responseToCharactersRemoteMapper: Mapper<CharactersResponse, List<CharacterRemote>>,
    private val characterRemoteToCharacterDomainMapper: Mapper<CharacterRemote, CharacterDomain>,
    private val dispatchers: DispatchersWrapper
): ICharactersRemoteSource, BaseRemoteSource() {
    override suspend fun getCharacters(pageIndex: Int): List<CharacterDomain>
    = withContext(dispatchers.io){
        val charactersRemote = responseToCharactersRemoteMapper.map(
            remoteService.getCharacters(pageIndex)
        )
        return@withContext characterRemoteToCharacterDomainMapper.map(charactersRemote)
    }

    override suspend fun getCharacter(id: Int): Reaction<CharacterDomain>
    = withContext(dispatchers.io) {
        return@withContext safeApiCall {
            characterRemoteToCharacterDomainMapper.map(
                remoteService.getCharacter(id)
            )
        }
    }
}
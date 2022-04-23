package com.shoshin.data.sources

import com.shoshin.data.entities.CharacterData
import com.shoshin.data.interfaces.services.ICharactersService
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.remote.entities.CharacterRemote
import com.shoshin.data.responses.CharactersResponse
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRemoteSource @Inject constructor(
    private val remoteService: ICharactersService,
    private val responseToCharactersRemoteMapper: Mapper<CharactersResponse, List<CharacterRemote>>,
    private val characterRemoteToDataMapper: Mapper<CharacterRemote, CharacterData>,
    private val dispatchers: DispatchersWrapper
): ICharactersRemoteSource, BaseRemoteSource() {

    override suspend fun getCharacters(pageIndex: Int): List<CharacterData>
    = withContext(dispatchers.io){
        val charactersRemote = responseToCharactersRemoteMapper.map(
            remoteService.getCharacters(pageIndex)
        )
        return@withContext characterRemoteToDataMapper.map(charactersRemote)
    }

    override suspend fun getCharacter(id: Int): Reaction<CharacterData>
    = withContext(dispatchers.io) {
        return@withContext safeApiCall {
            characterRemoteToDataMapper.map(
                remoteService.getCharacter(id)
            )
        }
    }
}
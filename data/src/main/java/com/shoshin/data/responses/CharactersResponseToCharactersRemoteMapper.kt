package com.shoshin.data.responses

import com.shoshin.data.remote.entities.CharacterRemote
import com.shoshin.domain.common.Mapper

class CharactersResponseToCharactersRemoteMapper :
    Mapper<CharactersResponse, List<CharacterRemote>>()
{
    override fun map(from: CharactersResponse): List<CharacterRemote> {
        return from.results ?: emptyList()
    }
}
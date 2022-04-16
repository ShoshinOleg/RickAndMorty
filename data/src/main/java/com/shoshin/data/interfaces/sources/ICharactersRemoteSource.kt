package com.shoshin.data.interfaces.sources

import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain

interface ICharactersRemoteSource {
    suspend fun getCharacters(pageIndex: Int): List<CharacterDomain>
    suspend fun getCharacter(id: Int): Reaction<CharacterDomain>
}
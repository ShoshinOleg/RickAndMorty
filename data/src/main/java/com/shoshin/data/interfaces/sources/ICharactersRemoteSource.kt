package com.shoshin.data.interfaces.sources

import com.shoshin.data.entities.CharacterData
import com.shoshin.domain.common.Reaction

interface ICharactersRemoteSource {
    suspend fun getCharacters(pageIndex: Int): List<CharacterData>
    suspend fun getCharacter(id: Int): Reaction<CharacterData>
}
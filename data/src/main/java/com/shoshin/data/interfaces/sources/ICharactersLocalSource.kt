package com.shoshin.data.interfaces.sources

import com.shoshin.data.entities.CharacterData

interface ICharactersLocalSource {
    suspend fun insertAll(characters: List<CharacterData>, pageIndex: Int)
    suspend fun getCharacters(page: Int): List<CharacterData>
    suspend fun getCharacter(id: Int): CharacterData
    suspend fun clearAll()
}
package com.shoshin.data.sources

import com.shoshin.data.db.CharacterRemoteKeysDao
import com.shoshin.data.db.CharactersDao
import com.shoshin.data.db.entities.CharacterDbo
import com.shoshin.data.db.entities.CharacterRemoteKeyDbo
import com.shoshin.data.entities.CharacterData
import com.shoshin.data.interfaces.sources.ICharactersLocalSource
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersLocalSource @Inject constructor(
    private val charactersDao: CharactersDao,
    private val characterKeysDao: CharacterRemoteKeysDao,
    private val dbToDataMapper: Mapper<CharacterDbo, CharacterData>,
    private val dataToDbMapper: Mapper<CharacterData, CharacterDbo>,
    private val dispatchers: DispatchersWrapper
): ICharactersLocalSource {

    override suspend fun insertAll(characters: List<CharacterData>, pageIndex: Int) = withContext(dispatchers.io) {
        val keys = characters.map { character ->
            CharacterRemoteKeyDbo(
                characterId = character.id,
                previousPage = if(pageIndex == CharactersPagingSource.FIRST_PAGE_INDEX) null else pageIndex - 1,
                page = pageIndex,
                nextPage = if(characters.size == CharactersPagingSource.PAGE_SIZE) pageIndex + 1 else null
            )
        }
        charactersDao.insertAll(dataToDbMapper.map(characters))
        characterKeysDao.insertAll(keys)
    }

    override suspend fun getCharacters(page: Int): List<CharacterData> =
        withContext(dispatchers.io) {
            val characterIndexes = characterKeysDao.getKeysByPage(page).map { key -> key.characterId }
            return@withContext if (characterIndexes.isNotEmpty())
                dbToDataMapper.map(
                    charactersDao.getCharacters(characterIndexes)
                )
            else
                listOf()
        }

    override suspend fun getCharacter(id: Int): CharacterData = withContext(dispatchers.io) {
        val characterDbo = charactersDao.getCharacter(id)
        return@withContext dbToDataMapper.map(characterDbo)
    }

    override suspend fun clearAll() {
        charactersDao.clear()
        characterKeysDao.clear()
    }
}
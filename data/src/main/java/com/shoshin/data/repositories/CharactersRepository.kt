package com.shoshin.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shoshin.data.entities.CharacterRemote
import com.shoshin.data.entities.CharacterRemoteToCharacterDomainMapper
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.sources.CharactersPagingSource
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersRemoteSource: ICharactersRemoteSource,
): ICharactersRepository {
    override fun getCharacters(): Flow<PagingData<CharacterDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = CharactersPagingSource.PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = 1000
            ),
            pagingSourceFactory = { CharactersPagingSource(charactersRemoteSource) }
        ).flow
    }

    override fun getCharacter(id: Int): Flow<Reaction<CharacterDomain>> = flow {
        emit(Reaction.Progress())
        emit(charactersRemoteSource.getCharacter(id))
    }
}
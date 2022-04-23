package com.shoshin.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shoshin.data.entities.CharacterData
import com.shoshin.data.interfaces.sources.ICharactersLocalSource
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.data.sources.CharactersPagingSource
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersLocalSource: ICharactersLocalSource,
    private val charactersRemoteSource: ICharactersRemoteSource,
    private val dataToDomainMapper: Mapper<CharacterData, CharacterDomain>,
): ICharactersRepository {
    override fun getCharacters(needRefresh: Boolean): Flow<PagingData<CharacterDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = CharactersPagingSource.PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = 1000
            ),
            pagingSourceFactory = {
                CharactersPagingSource(
                    charactersLocalSource,
                    charactersRemoteSource,
                    dataToDomainMapper
                )
            }
        ).flow.onStart {
            if (needRefresh)
                charactersLocalSource.clearAll()
        }
    }

    override fun getCharacter(id: Int): Flow<Reaction<CharacterDomain>> = flow {
        emit(Reaction.Progress())
        val character = dataToDomainMapper.map(charactersLocalSource.getCharacter(id))
        emit(Reaction.Progress(character))

        when(val reaction = charactersRemoteSource.getCharacter(id)) {
            is Reaction.Success -> emit(
                Reaction.Success(data = dataToDomainMapper.map(reaction.data))
            )
            is Reaction.Error -> emit(
                Reaction.Error(message = reaction.message, data = character)
            )
        }
    }
}
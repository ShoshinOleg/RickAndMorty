package com.shoshin.domain.usecases.interfaces

import androidx.paging.PagingData
import com.shoshin.domain.entities.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface IGetCharactersUseCase {
    fun getCharacters(needRefresh: Boolean = false): Flow<PagingData<CharacterDomain>>
}

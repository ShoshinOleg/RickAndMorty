package com.shoshin.domain.usecases.implementations

import androidx.paging.PagingData
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import com.shoshin.domain.usecases.interfaces.IGetCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: ICharactersRepository
): IGetCharactersUseCase {
    override fun getCharacters(needRefresh: Boolean): Flow<PagingData<CharacterDomain>> =
        repository.getCharacters(needRefresh)
}

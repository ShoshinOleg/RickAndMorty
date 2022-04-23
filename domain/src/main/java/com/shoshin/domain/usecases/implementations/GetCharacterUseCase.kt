package com.shoshin.domain.usecases.implementations

import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import com.shoshin.domain.usecases.interfaces.IGetCharacterUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: ICharactersRepository
): IGetCharacterUseCase {
    override fun getCharacter(id: Int): Flow<Reaction<CharacterDomain>> =
        repository.getCharacter(id)
}
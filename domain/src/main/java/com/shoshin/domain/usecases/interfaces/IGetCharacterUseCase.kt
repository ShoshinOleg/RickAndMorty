package com.shoshin.domain.usecases.interfaces

import com.shoshin.domain.common.Reaction
import com.shoshin.domain.entities.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface IGetCharacterUseCase {
    fun getCharacter(id: Int): Flow<Reaction<CharacterDomain>>
}

package com.shoshin.rickandmorty.fragments.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import com.shoshin.domain.usecases.interfaces.IGetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: IGetCharactersUseCase
): ViewModel() {

    fun getCharacters(needRefresh: Boolean = false): Flow<PagingData<CharacterDomain>> =
        getCharactersUseCase.getCharacters(needRefresh).cachedIn(viewModelScope)
}
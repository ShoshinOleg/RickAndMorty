package com.shoshin.rickandmorty.fragments.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoshin.domain.common.Reaction
import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import com.shoshin.domain.usecases.interfaces.IGetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: IGetCharacterUseCase,
    private val dispatchers: DispatchersWrapper
): ViewModel() {
    private val mutableCharacter = MutableLiveData<Reaction<CharacterDomain>>()
    val character: LiveData<Reaction<CharacterDomain>> = mutableCharacter

    fun getCharacter(id: Int) {
        viewModelScope.launch(dispatchers.io) {
            getCharacterUseCase.getCharacter(id).collectLatest { reaction ->
                withContext(dispatchers.main) {
                    mutableCharacter.value = reaction
                }
            }
        }
    }
}
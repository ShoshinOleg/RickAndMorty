package com.shoshin.rickandmorty.fragments.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shoshin.domain.common.dispatchers.DispatchersWrapper
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.repositories.ICharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: ICharactersRepository,
    private val dispatchers: DispatchersWrapper
): ViewModel() {

    private val mutableCharacters = MutableLiveData<PagingData<CharacterDomain>>()
    val characters: LiveData<PagingData<CharacterDomain>> = mutableCharacters

    fun getCharacters(needRefresh: Boolean = false) {
        viewModelScope.launch(dispatchers.io) {
            if(characters.value == null || needRefresh) {
                charactersRepository.getCharacters().cachedIn(viewModelScope).collectLatest { pagingData ->
                    withContext(dispatchers.main) {
                        mutableCharacters.value = pagingData
                    }
                }
            }
        }
    }
}
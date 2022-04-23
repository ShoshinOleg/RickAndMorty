package com.shoshin.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shoshin.data.entities.CharacterData
import com.shoshin.data.interfaces.sources.ICharactersLocalSource
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.domain.common.Mapper
import com.shoshin.domain.entities.CharacterDomain
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val charactersLocalSource: ICharactersLocalSource,
    private val charactersRemoteSource: ICharactersRemoteSource,
    private val dataToDomainMapper: Mapper<CharacterData, CharacterDomain>
): PagingSource<Int, CharacterDomain>() {

    companion object {
        const val PAGE_SIZE = 20
        const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDomain>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDomain> {
        val pageIndex = params.key ?: FIRST_PAGE_INDEX

        return try {
            val localCharacters = charactersLocalSource.getCharacters(pageIndex)
            val characters: List<CharacterData> = if (localCharacters.isEmpty()) {
                val characters = charactersRemoteSource.getCharacters(pageIndex)
                charactersLocalSource.insertAll(characters, pageIndex)
                characters
            } else {
                localCharacters
            }

            LoadResult.Page(
                data = dataToDomainMapper.map(characters),
                prevKey = if(pageIndex == FIRST_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if(characters.size == PAGE_SIZE) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
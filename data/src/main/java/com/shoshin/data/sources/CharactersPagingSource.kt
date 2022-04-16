package com.shoshin.data.sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shoshin.data.interfaces.sources.ICharactersRemoteSource
import com.shoshin.domain.entities.CharacterDomain
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val charactersRemoteSource: ICharactersRemoteSource
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
            val characters = charactersRemoteSource.getCharacters(pageIndex)
            LoadResult.Page(
                data = characters,
                prevKey = if(pageIndex == FIRST_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if(characters.size == PAGE_SIZE) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
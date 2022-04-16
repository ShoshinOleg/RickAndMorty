package com.shoshin.data.responses

import com.shoshin.data.entities.CharacterRemote

data class CharactersResponse(
    val info: CharactersResponseInfo? = null,
    val results: List<CharacterRemote>? = null
)
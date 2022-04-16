package com.shoshin.data.responses

data class CharactersResponseInfo(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
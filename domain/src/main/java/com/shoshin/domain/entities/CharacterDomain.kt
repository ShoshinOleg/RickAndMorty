package com.shoshin.domain.entities

import java.io.Serializable

data class CharacterDomain(
    val id: Int,
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val created: String? = null,
    val name: String? = null,
    val episode: List<String>? = null,
    val type: String? = null,
    val url: String? = null,
    val status: String? = null,
    val origin: OriginDomain? = null,
    val location: LocationDomain? = null
): Serializable
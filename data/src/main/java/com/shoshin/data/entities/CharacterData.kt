package com.shoshin.data.entities

data class CharacterData(
	val id: Int,
	val image: String? = null,
	val gender: String? = null,
	val species: String? = null,
	val created: String? = null,
	val origin: OriginData? = null,
	val name: String? = null,
	val location: LocationData? = null,
	val episode: List<String>? = null,
	val type: String? = null,
	val url: String? = null,
	val status: String? = null
)
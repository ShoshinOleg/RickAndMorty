package com.shoshin.data.db.entities

import com.shoshin.data.entities.CharacterData
import com.shoshin.domain.common.Mapper

class CharacterDataToDbMapper: Mapper<CharacterData, CharacterDbo>() {
    override fun map(from: CharacterData) = CharacterDbo(
        id = from.id ?: 0,
        image = from.image,
        gender = from.gender,
        species = from.species,
        created = from.created,
        name = from.name,
        episode = from.episode,
        type = from.type,
        url = from.url,
        status = from.status,
        location = LocationDbo(
            name = from.location?.name,
            url = from.location?.url
        ),
        origin = OriginDbo(
            name = from.origin?.name,
            url = from.origin?.url
        )
    )
}

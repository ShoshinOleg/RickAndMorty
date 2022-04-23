package com.shoshin.data.entities

import com.shoshin.domain.common.Mapper
import com.shoshin.domain.entities.CharacterDomain
import com.shoshin.domain.entities.LocationDomain
import com.shoshin.domain.entities.OriginDomain

class CharacterDataToDomainMapper: Mapper<CharacterData, CharacterDomain>() {
    override fun map(from: CharacterData) = CharacterDomain(
        id = from.id,
        image = from.image,
        gender = from.gender,
        species = from.species,
        created = from.created,
        name = from.name,
        episode = from.episode,
        type = from.type,
        url = from.url,
        status = from.status,
        location = LocationDomain(
            name = from.location?.name,
            url = from.location?.url
        ),
        origin = OriginDomain(
            name = from.origin?.name,
            url = from.origin?.url
        )
    )
}

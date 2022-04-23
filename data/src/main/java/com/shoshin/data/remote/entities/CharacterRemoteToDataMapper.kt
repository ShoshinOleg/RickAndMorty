package com.shoshin.data.remote.entities

import com.shoshin.data.entities.CharacterData
import com.shoshin.data.entities.LocationData
import com.shoshin.data.entities.OriginData
import com.shoshin.domain.common.Mapper

class CharacterRemoteToDataMapper: Mapper<CharacterRemote, CharacterData>() {
    override fun map(from: CharacterRemote) = CharacterData(
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
        location = LocationData(
            name = from.location?.name,
            url = from.location?.url
        ),
        origin = OriginData(
            name = from.origin?.name,
            url = from.origin?.url
        )
    )
}

package com.shoshin.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_keys")
data class CharacterRemoteKeyDbo(
    @PrimaryKey
    val characterId: Int,
    val previousPage: Int? = null,
    val page: Int,
    val nextPage: Int? = null
)
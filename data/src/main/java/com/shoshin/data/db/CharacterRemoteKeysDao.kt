package com.shoshin.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shoshin.data.db.entities.CharacterRemoteKeyDbo

@Dao
interface CharacterRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterRemoteKeyDbo>)

    @Query("SELECT * FROM character_keys WHERE page=:page")
    suspend fun getKeysByPage(page: Int): List<CharacterRemoteKeyDbo>

    @Query("DELETE FROM character_keys")
    suspend fun clear()
}

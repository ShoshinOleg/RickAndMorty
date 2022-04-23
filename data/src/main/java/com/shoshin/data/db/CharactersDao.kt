package com.shoshin.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shoshin.data.db.entities.CharacterDbo

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CharacterDbo>)

    @Query("SELECT * FROM characters WHERE id IN (:characterIds)")
    suspend fun getCharacters(characterIds: List<Int>): List<CharacterDbo>

    @Query("SELECT * from characters WHERE id=:id")
    suspend fun getCharacter(id: Int): CharacterDbo

    @Query("DELETE FROM characters")
    suspend fun clear()
}

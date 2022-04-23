package com.shoshin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shoshin.data.db.entities.CharacterDbo
import com.shoshin.data.db.entities.CharacterRemoteKeyDbo

@Database(
    entities = [CharacterDbo::class, CharacterRemoteKeyDbo::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun remoteKeysDao(): CharacterRemoteKeysDao
}

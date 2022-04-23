package com.shoshin.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun from(list: List<String>?): String =
        if(list == null) "" else Gson().toJson(list)

    @TypeConverter
    fun to(serializedList: String): List<String> =
        Gson().fromJson(serializedList, object : TypeToken<List<String>>() {}.type)
}

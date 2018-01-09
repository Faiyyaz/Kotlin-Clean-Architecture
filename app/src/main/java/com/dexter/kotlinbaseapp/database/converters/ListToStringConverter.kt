package com.dexter.kotlinbaseapp.database.converters

import android.arch.persistence.room.TypeConverter
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Khatr on 12/31/2017.
 */

class ListToStringConverter {
    @TypeConverter
    fun fromString(value: String): List<DetailResponse.RatingsBean>? {
        val listType = object : TypeToken<List<DetailResponse.RatingsBean>>() {}.type
        return Gson().fromJson<List<DetailResponse.RatingsBean>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<DetailResponse.RatingsBean>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}

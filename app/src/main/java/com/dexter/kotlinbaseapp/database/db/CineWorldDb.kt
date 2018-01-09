package com.dexter.kotlinbaseapp.database.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.database.beans.SearchBean
import com.dexter.kotlinbaseapp.database.converters.ListToStringConverter
import com.dexter.kotlinbaseapp.database.dao.DetailDao
import com.dexter.kotlinbaseapp.database.dao.SearchDao

/**
 * Created by Khatr on 12/31/2017.
 */

@Database(entities = [(SearchBean::class), (DetailResponse::class)], version = 1, exportSchema = false)
@TypeConverters(ListToStringConverter::class)
abstract class CineWorldDb : RoomDatabase() {
    abstract fun searchDao(): SearchDao

    abstract fun detailDao(): DetailDao
}

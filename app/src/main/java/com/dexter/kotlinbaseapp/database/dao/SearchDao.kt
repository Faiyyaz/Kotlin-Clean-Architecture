package com.dexter.kotlinbaseapp.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dexter.kotlinbaseapp.database.beans.SearchBean
import com.dexter.kotlinbaseapp.utils.Constants
import io.reactivex.Flowable

/**
 * Created by Khatr on 12/31/2017.
 */

@Dao
interface SearchDao {

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " Where Title LIKE:search")
    fun getSearchResult(search: String): Flowable<List<SearchBean>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchBeanList: List<SearchBean>)

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    fun deleteAll()
}

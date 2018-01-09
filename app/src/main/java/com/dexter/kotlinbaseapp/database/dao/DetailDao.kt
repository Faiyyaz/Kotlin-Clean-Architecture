package com.dexter.kotlinbaseapp.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.utils.Constants
import io.reactivex.Flowable

/**
 * Created by Admin on 09-01-2018.
 */

@Dao
interface DetailDao {
    @Query("SELECT * FROM " + Constants.TABLE_DETAIL + " Where imdbID = :imdbID")
    abstract fun getSearchResult(imdbID: String): Flowable<DetailResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(detailResponse: DetailResponse)

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    abstract fun deleteAll()
}
package com.dexter.kotlinbaseapp.database.beans

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.dexter.kotlinbaseapp.utils.Constants

/**
 * Created by Admin on 09-01-2018.
 */

@Entity(tableName = Constants.TABLE_NAME)
class SearchBean {

    /**
     * Title : Guardians of the Galaxy
     * Year : 2014
     * imdbID : tt2015381
     * Type : movie
     * Poster : https://images-na.ssl-images-amazon.com/images/M/MV5BMTAwMjU5OTgxNjZeQTJeQWpwZ15BbWU4MDUxNDYxODEx._V1_SX300.jpg
     */

    var title: String? = null
    var year: String? = null
    @NonNull @PrimaryKey
    var imdbID: String? = null
    var type: String? = null
    var poster: String? = null
}
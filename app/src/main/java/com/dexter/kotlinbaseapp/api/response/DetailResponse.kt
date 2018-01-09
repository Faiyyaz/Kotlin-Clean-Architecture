package com.dexter.kotlinbaseapp.api.response

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.dexter.kotlinbaseapp.utils.Constants

/**
 * Created by Admin on 09-01-2018.
 */

@Entity(tableName = Constants.TABLE_DETAIL)
class DetailResponse {

    /**
     * Title : Guardians of the Galaxy
     * Year : 2014
     * Rated : PG-13
     * Released : 01 Aug 2014
     * Runtime : 121 min
     * Genre : Action, Adventure, Sci-Fi
     * Director : James Gunn
     * Writer : James Gunn, Nicole Perlman, Dan Abnett (based on the Marvel comics by), Andy Lanning (based on the Marvel comics by), Bill Mantlo (character created by: Rocket Raccoon), Keith Giffen (character created by: Rocket Raccoon), Jim Starlin (characters created by: Drax the Destroyer,  Gamora & Thanos), Steve Englehart (character created by: Star-Lord), Steve Gan (character created by: Star-Lord), Steve Gerber (character created by: Howard the Duck), Val Mayerik (character created by: Howard the Duck)
     * Actors : Chris Pratt, Zoe Saldana, Dave Bautista, Vin Diesel
     * Plot : After stealing a mysterious orb in the far reaches of outer space, Peter Quill from Earth, is now the main target of a manhunt led by the villain known as Ronan the Accuser. To help fight Ronan and his team and save the galaxy from his power, Quill creates a team of space heroes known as the "Guardians of the Galaxy" to save the world.
     * Language : English
     * Country : USA, UK
     * Awards : Nominated for 2 Oscars. Another 52 wins & 99 nominations.
     * Poster : https://images-na.ssl-images-amazon.com/images/M/MV5BMTAwMjU5OTgxNjZeQTJeQWpwZ15BbWU4MDUxNDYxODEx._V1_SX300.jpg
     * Ratings : [{"Source":"Internet Movie Database","Value":"8.1/10"},{"Source":"Rotten Tomatoes","Value":"91%"},{"Source":"Metacritic","Value":"76/100"}]
     * Metascore : 76
     * imdbRating : 8.1
     * imdbVotes : 803,714
     * imdbID : tt2015381
     * Type : movie
     * DVD : 09 Dec 2014
     * BoxOffice : $270,592,504
     * Production : Walt Disney Pictures
     * Website : http://marvel.com/guardians
     * Response : True
     */

    var title: String? = null
    var year: String? = null
    var rated: String? = null
    var released: String? = null
    var runtime: String? = null
    var genre: String? = null
    var director: String? = null
    var writer: String? = null
    var actors: String? = null
    var plot: String? = null
    var language: String? = null
    var country: String? = null
    var awards: String? = null
    var poster: String? = null
    var metascore: String? = null
    var imdbRating: String? = null
    var imdbVotes: String? = null
    @NonNull @PrimaryKey
    var imdbID: String? = null
    var type: String? = null
    var dvd: String? = null
    var boxOffice: String? = null
    var production: String? = null
    var website: String? = null
    var response: String? = null
    var ratings: List<RatingsBean>? = null

    class RatingsBean {
        /**
         * Source : Internet Movie Database
         * Value : 8.1/10
         */

        var source: String? = null
        var value: String? = null
    }
}
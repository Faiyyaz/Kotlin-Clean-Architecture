package com.dexter.kotlinbaseapp.api.service

import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.api.response.SearchResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

/**
 * Created by Admin on 09-01-2018.
 */

interface OMDBApiInterface {

    @GET("/")
    fun getSearchResult(@QueryMap map: HashMap<String, String>): Flowable<SearchResponse>

    @GET("/")
    fun getDetails(@QueryMap map: HashMap<String, String>): Flowable<DetailResponse>
}
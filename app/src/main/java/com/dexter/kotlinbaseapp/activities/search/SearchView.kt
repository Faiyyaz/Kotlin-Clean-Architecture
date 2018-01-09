package com.dexter.kotlinbaseapp.activities.search

import com.dexter.kotlinbaseapp.api.response.SearchResponse
import com.dexter.kotlinbaseapp.database.beans.SearchBean

/**
 * Created by Khatr on 12/31/2017.
 */

interface SearchView {

    fun showProgressDialog()

    fun removeProgressDialog()

    fun onFailure(error: String)

    fun onSuccess(searchResponse: SearchResponse)

    fun onCacheSuccess(searchBeanList: List<SearchBean>)
}

package com.dexter.kotlinbaseapp.activities.search

import com.dexter.kotlinbaseapp.BuildConfig
import com.dexter.kotlinbaseapp.api.response.SearchResponse
import com.dexter.kotlinbaseapp.database.beans.SearchBean
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import java.util.*

/**
 * Created by Khatr on 12/31/2017.
 */

class SearchPresenter {

    private var searchApiCall: SearchApiCall? = null
    private var searchView: SearchView? = null
    private var searchTerm: String? = null
    private var cineWorldDb: CineWorldDb? = null
    private var page: Int = 0
    private var shouldFetchFromDatabase: Boolean = false

    internal fun setSearchApiCall(searchApiCall: SearchApiCall) {
        this.searchApiCall = searchApiCall
    }

    internal fun setSearchView(searchView: SearchView) {
        this.searchView = searchView
    }

    internal fun setSearchTerm(searchTerm: String) {
        this.searchTerm = searchTerm
    }

    internal fun setPage(page: Int) {
        this.page = page
    }

    internal fun setShouldFetchFromDatabase(shouldFetchFromDatabase: Boolean) {
        this.shouldFetchFromDatabase = shouldFetchFromDatabase
    }

    fun setCineWorldDb(cineWorldDb: CineWorldDb) {
        this.cineWorldDb = cineWorldDb
    }

    internal fun getSearchResponse() {
        searchView!!.showProgressDialog()

        val map = HashMap<String, String>()
        map.put(Constants.apiKey, BuildConfig.API_KEY)
        searchTerm?.let { map.put(Constants.searchTerm, it) }
        map.put(Constants.page, page.toString())

        cineWorldDb?.let {
            searchApiCall!!.getApiData(it, shouldFetchFromDatabase, map, object : SearchApiCall.GetSearchBeanCallback {
                override fun onNext(searchResponse: SearchResponse) {
                    searchView!!.removeProgressDialog()
                    searchView!!.onSuccess(searchResponse)
                }

                override fun onCacheNext(searchBeanList: List<SearchBean>) {
                    searchView!!.removeProgressDialog()
                    searchView!!.onCacheSuccess(searchBeanList)
                }

                override fun onError(error: String) {
                    searchView!!.removeProgressDialog()
                    searchView!!.onFailure(error)
                }
            })
        }
    }
}

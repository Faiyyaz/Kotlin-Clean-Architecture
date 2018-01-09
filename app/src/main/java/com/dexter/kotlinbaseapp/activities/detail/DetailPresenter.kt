package com.dexter.kotlinbaseapp.activities.detail

import com.dexter.kotlinbaseapp.BuildConfig
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import java.util.*

/**
 * Created by Khatr on 12/31/2017.
 */

class DetailPresenter {

    private var detailApiCall: DetailApiCall? = null
    private var detailView: DetailView? = null
    private var imdbId: String? = null
    private var shouldFetchFromCache: Boolean = false
    private var cineWorldDb: CineWorldDb? = null

    internal fun setDetailApiCall(detailApiCall: DetailApiCall) {
        this.detailApiCall = detailApiCall
    }

    internal fun setDetailView(detailView: DetailView) {
        this.detailView = detailView
    }

    internal fun setImdbId(imdbId: String) {
        this.imdbId = imdbId
    }

    internal fun setShouldFetchFromCache(shouldFetchFromCache: Boolean) {
        this.shouldFetchFromCache = shouldFetchFromCache
    }

    fun setCineWorldDb(cineWorldDb: CineWorldDb) {
        this.cineWorldDb = cineWorldDb
    }

    internal fun getDetailResponse() {
        detailView!!.showProgressDialog()

        val map = HashMap<String, String>()
        map.put(Constants.apiKey, BuildConfig.API_KEY)
        map.put(Constants.id, imdbId.toString())
        map.put(Constants.plot, Constants.full)

        cineWorldDb?.let {
            detailApiCall!!.getApiData(it, shouldFetchFromCache, map, object : DetailApiCall.GetDetailCallback {
                override fun onNext(detailResponse: DetailResponse) {
                    detailView!!.removeProgressDialog()
                    detailView!!.onSuccess(detailResponse)
                }

                override fun onError(error: String) {
                    detailView!!.removeProgressDialog()
                    detailView!!.onFailure(error)
                }
            })
        }
    }
}

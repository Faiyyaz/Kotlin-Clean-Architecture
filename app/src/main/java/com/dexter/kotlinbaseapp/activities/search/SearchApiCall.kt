package com.dexter.kotlinbaseapp.activities.search

import android.os.AsyncTask
import com.dexter.kotlinbaseapp.api.response.SearchResponse
import com.dexter.kotlinbaseapp.api.service.OMDBApiInterface
import com.dexter.kotlinbaseapp.database.beans.SearchBean
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import com.dexter.kotlinbaseapp.utils.NetworkUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Khatr on 12/31/2017.
 */

class SearchApiCall(private val omdbApiInterface: OMDBApiInterface) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null

    internal fun getApiData(cineWorldDb: CineWorldDb, shouldFetchFromDatabase: Boolean, map: HashMap<String, String>, callback: GetSearchBeanCallback) {
        if (shouldFetchFromDatabase) {
            val s = "%" + map[Constants.searchTerm] + "%"
            val searchResponseFlowable = cineWorldDb.searchDao().getSearchResult(s)
            getResponseFromCache(cineWorldDb, map, searchResponseFlowable, callback)
        } else {
            getResponseFromApi(cineWorldDb, map, callback)
        }
    }

    private fun doApiRequest(map: HashMap<String, String>): Flowable<SearchResponse> {
        return omdbApiInterface.getSearchResult(map)
    }

    private fun getResponseFromApi(cineWorldDb: CineWorldDb, map: HashMap<String, String>, callback: GetSearchBeanCallback) {
        disposable = doApiRequest(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe({ searchResponse -> onNext(cineWorldDb, searchResponse, callback) }, { throwable -> callback.onError(NetworkUtils.getStringError(throwable)) }, Action { this.onComplete() })*/
                .subscribe({ onNext(cineWorldDb, searchResponse = it, callback = callback) }, { callback.onError(NetworkUtils.getStringError(Throwable())) }, { this.onComplete() })
        compositeDisposable.add(disposable!!)
    }

    private fun onNext(cineWorldDb: CineWorldDb, searchResponse: SearchResponse, callback: GetSearchBeanCallback) {
        if (searchResponse.getResponse().equals("true", true)) {
            InsertTask(cineWorldDb, searchResponse).execute()
            callback.onNext(searchResponse)
        } else {
            callback.onError("Result Not Found")
        }
    }

    private fun getResponseFromCache(cineWorldDb: CineWorldDb, map: HashMap<String, String>, searchResponseFlowable: Flowable<List<SearchBean>>, callback: GetSearchBeanCallback) {
        disposable = searchResponseFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /* .subscribe({ searchBeans -> onCacheNext(searchBeans, callback, cineWorldDb, map) }, { throwable -> callback.onError(NetworkUtils.getStringError(throwable)) }, Action { this.onComplete() })*/
                .subscribe({ onCacheNext(searchBeanList = it, callback = callback, cineWorldDb = cineWorldDb, map = map) }, { callback.onError(NetworkUtils.getStringError(Throwable())) }, { this.onComplete() })
        compositeDisposable.add(disposable!!)
    }

    private fun onCacheNext(searchBeanList: List<SearchBean>?, callback: GetSearchBeanCallback, cineWorldDb: CineWorldDb, map: HashMap<String, String>) {
        if (searchBeanList != null && !searchBeanList.isEmpty()) {
            callback.onCacheNext(searchBeanList)
        } else {
            getResponseFromApi(cineWorldDb, map, callback)
        }
    }

    interface GetSearchBeanCallback {
        fun onNext(searchResponse: SearchResponse)

        fun onCacheNext(searchBeanList: List<SearchBean>)

        fun onError(error: String)
    }

    private fun onComplete() {
        disposable!!.dispose()
        compositeDisposable.clear()
    }

    private class InsertTask internal constructor(private val cineWorldDb: CineWorldDb, private val searchResponse: SearchResponse) : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg strings: String): String? {
            cineWorldDb.searchDao().insert(searchResponse.getSearch()!!)
            return null
        }
    }
}

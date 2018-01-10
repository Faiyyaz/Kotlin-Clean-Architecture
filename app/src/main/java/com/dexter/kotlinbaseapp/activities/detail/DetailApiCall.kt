package com.dexter.kotlinbaseapp.activities.detail

import android.os.AsyncTask
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.api.service.OMDBApiInterface
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

class DetailApiCall(private val omdbApiInterface: OMDBApiInterface) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null

    internal fun getApiData(cineWorldDb: CineWorldDb, shouldFetchFromDatabase: Boolean, map: HashMap<String, String>, callback: GetDetailCallback) {
        if (shouldFetchFromDatabase) {
            val s = map[Constants.id]
            val detailBeanFlowable = cineWorldDb.detailDao().getSearchResult(s!!)
            getResponseFromCache(detailBeanFlowable, callback)
        } else {
            getResponseFromApi(cineWorldDb, map, callback)
        }
    }

    private fun doApiRequest(map: HashMap<String, String>): Flowable<DetailResponse> {
        return omdbApiInterface.getDetails(map)
    }

    private fun getResponseFromApi(cineWorldDb: CineWorldDb, map: HashMap<String, String>, callback: GetDetailCallback) {
        disposable = doApiRequest(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe({ detailResponse -> onNext(cineWorldDb, detailResponse, callback) }, { throwable -> callback.onError(NetworkUtils.getStringError(throwable)) }, Action { this.onComplete() })*/
                .subscribe({ onNext(cineWorldDb, detailResponse = it, callback = callback) }, { callback.onError(NetworkUtils.getStringError(Throwable())) }, { this.onComplete() })
        compositeDisposable.add(disposable!!)
    }

    private fun onNext(cineWorldDb: CineWorldDb, detailResponse: DetailResponse, callback: GetDetailCallback) {
        if (detailResponse.response.equals("true", true)) {
            InsertTask(cineWorldDb, detailResponse).execute()
            callback.onNext(detailResponse)
        } else {
            callback.onError("Result Not Found")
        }
    }

    private fun onComplete() {
        disposable!!.dispose()
        compositeDisposable.clear()
    }

    private fun getResponseFromCache(detailBeanFlowable: Flowable<DetailResponse>, callback: GetDetailCallback) {
        disposable = detailBeanFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(Consumer<DetailResponse> { callback.onNext(it) }, { throwable -> callback.onError(NetworkUtils.getStringError(throwable)) }, Action { this.onComplete() }) */
                .subscribe({ callback.onNext(detailResponse = it) }, { callback.onError(NetworkUtils.getStringError(Throwable())) }, { this.onComplete() })
        compositeDisposable.add(disposable!!)
    }

    interface GetDetailCallback {
        fun onNext(detailResponse: DetailResponse)

        fun onError(error: String)
    }

    private class InsertTask internal constructor(private val cineWorldDb: CineWorldDb, private val detailResponse: DetailResponse) : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg strings: String): String? {
            cineWorldDb.detailDao().insert(detailResponse)
            return null
        }
    }
}

package com.dexter.kotlinbaseapp.activities.search

import android.app.AlertDialog
import android.text.TextUtils
import android.view.Menu
import android.view.WindowManager
import com.dexter.kotlinbaseapp.R
import com.dexter.kotlinbaseapp.activities.BaseActivity
import com.dexter.kotlinbaseapp.api.response.SearchResponse
import com.dexter.kotlinbaseapp.database.beans.SearchBean
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import com.dexter.kotlinbaseapp.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

/**
 * Created by Khatr on 12/31/2017.
 */

class SearchActivity : BaseActivity(), SearchView {

    @Inject
    internal var searchApiCall: SearchApiCall? = null
    @Inject
    internal var cineWorldDb: CineWorldDb? = null

    private var totalSearchResult: String? = null
    private var searchPresenter: SearchPresenter? = null
    private var size: Int = 0
    private var page = 1
    private var dialog: AlertDialog? = null
    private var searchView: android.support.v7.widget.SearchView? = null

    override fun getTag(): String? {
        return TAG
    }

    override fun getLayout(): Int {
        return R.layout.activity_search
    }

    override fun onActivityCreated() {
        initUi()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        activityComponent().inject(this)
    }

    override fun showProgressDialog() {
        dialog = DialogUtils.getDialog(this, getString(R.string.please_wait))
        dialog!!.show()
    }

    override fun removeProgressDialog() {
        dialog!!.dismiss()
    }

    override fun onFailure(error: String) {
        showMessage(Constants.error, error)
    }

    override fun onSuccess(searchResponse: SearchResponse) {

    }

    override fun onCacheSuccess(searchBeanList: List<SearchBean>) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.search_menu, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        searchView = myActionMenuItem.actionView as android.support.v7.widget.SearchView
        searchView!!.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                if (!TextUtils.isEmpty(query)) {
                    searchPresenter = SearchPresenter()
                    searchPresenter!!.setSearchView(this@SearchActivity)
                    searchPresenter!!.setSearchTerm(query)
                    searchPresenter!!.setPage(page)
                    searchPresenter!!.setShouldFetchFromDatabase(!isNetworkConnected())
                    cineWorldDb?.let { searchPresenter!!.setCineWorldDb(it) }
                    searchApiCall?.let { searchPresenter!!.setSearchApiCall(it) }
                    searchPresenter!!.getSearchResponse()
                    page = 1
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return true
    }

    companion object {

        private val TAG = "SearchActivity"
    }
}

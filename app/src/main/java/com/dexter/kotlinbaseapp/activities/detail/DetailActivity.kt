package com.dexter.kotlinbaseapp.activities.detail

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.dexter.kotlinbaseapp.R
import com.dexter.kotlinbaseapp.activities.BaseActivity
import com.dexter.kotlinbaseapp.api.response.DetailResponse
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import com.dexter.kotlinbaseapp.utils.DialogUtils
import javax.inject.Inject

/**
 * Created by Khatr on 12/31/2017.
 */

class DetailActivity : BaseActivity(), DetailView {

    @Inject
    lateinit var detailApiCall: DetailApiCall

    @Inject
    lateinit var cineWorldDb: CineWorldDb

    private var imdbID: String? = null
    private var dialog: AlertDialog? = null

    override fun getTag(): String? {
        return TAG
    }

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun onActivityCreated() {

        val extras = getIntent().getExtras()
        if (extras != null) {
            imdbID = extras!!.getString(Constants.imdbID)
            val title = extras!!.getString(Constants.title)

            val supportActionBar = supportActionBar
            if (supportActionBar != null) {
                supportActionBar!!.title = title
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            }
        }

        iniUi()
    }

    private fun iniUi() {
        activityComponent().inject(this)

        val detailPresenter = DetailPresenter()
        detailPresenter.setDetailView(this)
        detailPresenter.setShouldFetchFromCache(!isNetworkConnected())
        detailPresenter.setImdbId(imdbID.toString())
        cineWorldDb?.let { detailPresenter.setCineWorldDb(it) }
        detailApiCall?.let { detailPresenter.setDetailApiCall(it) }
        detailPresenter.getDetailResponse()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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

    override fun onSuccess(detailResponse: DetailResponse) {

        /*val poster = ImageUtils.getOriginalPoster(detailResponse.getPoster())
        ImageUtils.setRectangleImage(this, ivPoster, poster)
        TextUtils.setString(tvTitle, detailResponse.getTitle())

        val ratings = TextUtils.getStringFromStringResId(this, R.string.ratings)
        TextUtils.setString(tvRatings, ratings)
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvType, TextUtils.getStringFromStringResId(this, R.string.type), detailResponse.getType())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvGenre, TextUtils.getStringFromStringResId(this, R.string.genre), detailResponse.getGenre())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvRated, TextUtils.getStringFromStringResId(this, R.string.rated), detailResponse.getRated())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvReleased, TextUtils.getStringFromStringResId(this, R.string.released), detailResponse.getReleased())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvRuntime, TextUtils.getStringFromStringResId(this, R.string.runtime), detailResponse.getRuntime())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvImdbRating, TextUtils.getStringFromStringResId(this, R.string.imdbRating), detailResponse.getImdbRating())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvPlot, TextUtils.getStringFromStringResId(this, R.string.plot), detailResponse.getPlot())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvActors, TextUtils.getStringFromStringResId(this, R.string.actors), detailResponse.getActors())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvWriter, TextUtils.getStringFromStringResId(this, R.string.writers), detailResponse.getWriter())
        TextUtils.setSpannableString(this, R.color.colorGoldDark, tvWriter, TextUtils.getStringFromStringResId(this, R.string.awards), detailResponse.getAwards())

        this.ratings.addAll(detailResponse.getRatings())
        adapter!!.notifyDataSetChanged()*/
    }

    companion object {

        private val TAG = "DetailActivity"

        fun getStartIntent(context: Context, imdbID: String, title: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Constants.imdbID, imdbID)
            intent.putExtra(Constants.title, title)
            return intent
        }
    }
}

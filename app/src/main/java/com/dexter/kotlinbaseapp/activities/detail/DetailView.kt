package com.dexter.kotlinbaseapp.activities.detail

import com.dexter.kotlinbaseapp.api.response.DetailResponse

/**
 * Created by Khatr on 12/31/2017.
 */

interface DetailView {

    fun showProgressDialog()

    fun removeProgressDialog()

    fun onFailure(error: String)

    fun onSuccess(detailResponse: DetailResponse)
}

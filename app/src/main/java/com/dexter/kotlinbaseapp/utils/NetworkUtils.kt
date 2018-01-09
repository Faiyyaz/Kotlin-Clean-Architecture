package com.dexter.kotlinbaseapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Admin on 09-01-2018.
 */

object NetworkUtils {
    private val DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again."
    private val NETWORK_ERROR_MESSAGE = "No Internet Connection!"
    private val ERROR_MESSAGE_HEADER = "Error-Message"

    /**
     *  Function to check whether internet is available or not
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.activeNetworkInfo
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     *  Function to get the throwable into string
     */
    fun getStringError(error: Throwable): String {
        error.printStackTrace()
        if (error is IOException)
            return NETWORK_ERROR_MESSAGE
        else if (error !is HttpException) {
            return if (error is NullPointerException) {
                error.getLocalizedMessage()
            } else {
                DEFAULT_ERROR_MESSAGE
            }
        } else {
            val response = error.response()
            if (response != null) {
                val status = getJsonStringFromResponse(response)
                if (!android.text.TextUtils.isEmpty(status)) return status!!

                val headers = response.headers().toMultimap()
                if (headers.containsKey(ERROR_MESSAGE_HEADER))
                    return headers[ERROR_MESSAGE_HEADER]!![0]
            }
        }
        return ""
    }

    /**
     *   Function to get the error response returned in the API
     */
    private fun getJsonStringFromResponse(response: retrofit2.Response<*>): String? {
        return try {
            response.errorBody()!!.string()
        } catch (e: Exception) {
            null
        }

    }
}
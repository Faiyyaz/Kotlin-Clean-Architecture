package com.dexter.kotlinbaseapp.activities

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dexter.kotlinbaseapp.MyApp
import com.dexter.kotlinbaseapp.R
import com.dexter.kotlinbaseapp.dagger.components.ActivityComponent
import com.dexter.kotlinbaseapp.dagger.components.DaggerActivityComponent
import com.dexter.kotlinbaseapp.dagger.module.ActivityModule
import com.dexter.kotlinbaseapp.interfaces.BaseView
import com.dexter.kotlinbaseapp.utils.Constants
import com.dexter.kotlinbaseapp.utils.NetworkUtils
import es.dmoral.toasty.Toasty
import timber.log.Timber

/**
 * Created by Admin on 09-01-2018.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var mActivityComponent: ActivityComponent? = null

    /**
     * initialising dagger ActivityComponent here
     */
    fun activityComponent(): ActivityComponent {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .appComponent(MyApp.getAppComponent())
                    .build()
        }
        return mActivityComponent as ActivityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        /**
         * adding tag to timber here
         */
        Timber.tag(getTag())

        onActivityCreated()

        if (!isNetworkConnected()) {
            showMessage(Constants.error, getString(R.string.no_internet_available))
        }
    }

    /**
     * Pass the layout ID here
     *
     * @return the layout resource ID
     */
    abstract @LayoutRes
    fun getLayout(): Int

    /**
     * Here we get the onCreate of the class
     */
    abstract fun onActivityCreated()

    /**
     * Add the class name here
     */
    abstract fun getTag(): String?

    /**
     * initialising toasty to show different type of toast
     */
    override fun showMessage(type: String, message: String) {
        when (type) {
            Constants.error -> Toasty.error(this, message, Toast.LENGTH_LONG, true).show()
            Constants.success -> Toasty.success(this, message, Toast.LENGTH_LONG, true).show()
            Constants.info -> Toasty.info(this, message, Toast.LENGTH_LONG, true).show()
            Constants.warning -> Toasty.warning(this, message, Toast.LENGTH_LONG, true).show()
            Constants.normal -> Toasty.normal(this, message, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Check status of internet connection
     */
    private fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkAvailable(this)
    }
}
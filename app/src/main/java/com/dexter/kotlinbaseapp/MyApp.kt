package com.dexter.kotlinbaseapp

import android.app.Application
import android.content.res.Resources
import com.dexter.kotlinbaseapp.dagger.components.AppComponent
import com.dexter.kotlinbaseapp.dagger.components.DaggerAppComponent
import com.dexter.kotlinbaseapp.dagger.module.AppModule
import com.dexter.kotlinbaseapp.dagger.module.NetworkModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by Admin on 09-01-2018.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        sInstance = this
        sAppComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule())
                .appModule(AppModule(this))
                .build()

        initTimber()
        initLeakCanary()
    }

    companion object {

        private var sAppComponent: AppComponent? = null
        private var sInstance: MyApp? = null

        /**
         * Function to get appComponent using dagger
         */
        fun getAppComponent(): AppComponent? {
            return sAppComponent
        }

        /**
         * Function to get resources
         */
        fun getRes(): Resources {
            return sInstance!!.resources
        }
    }

    /**
     * initialising Timber Here
     */
    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Function to get instance of app class Timber
     */
    fun getInstance(): MyApp? {
        return sInstance
    }

    /**
     * initialising leakCanary here
     */
    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
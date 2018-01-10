package com.dexter.kotlinbaseapp.dagger.components


import android.content.Context
import android.content.res.Resources
import com.dexter.kotlinbaseapp.activities.detail.DetailApiCall
import com.dexter.kotlinbaseapp.activities.search.SearchApiCall
import com.dexter.kotlinbaseapp.dagger.module.AppModule
import com.dexter.kotlinbaseapp.dagger.module.NetworkModule
import com.dexter.kotlinbaseapp.dagger.qualifier.AppContext
import com.dexter.kotlinbaseapp.dagger.scopes.PerApplication
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import dagger.Component

@PerApplication
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    @AppContext
    fun appContext(): Context

    fun resources(): Resources

    fun cineWorldDb() : CineWorldDb

    fun searchApiCall() : SearchApiCall

    fun detailApiCall() : DetailApiCall

    /**
     * add Database & Api calls here
     */
}

package com.dexter.kotlinbaseapp.dagger.components


import android.content.Context
import android.content.res.Resources
import com.dexter.kotlinbaseapp.dagger.module.AppModule
import com.dexter.kotlinbaseapp.dagger.module.NetworkModule
import com.dexter.kotlinbaseapp.dagger.qualifier.AppContext
import com.dexter.kotlinbaseapp.dagger.scopes.PerApplication
import dagger.Component

@PerApplication
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    @AppContext
    fun appContext(): Context

    fun resources(): Resources

    /**
     * add Database & Api calls here
     */
}

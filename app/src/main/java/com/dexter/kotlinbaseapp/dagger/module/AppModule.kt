package com.dexter.kotlinbaseapp.dagger.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.res.Resources
import com.dexter.kotlinbaseapp.dagger.qualifier.AppContext
import com.dexter.kotlinbaseapp.dagger.scopes.PerApplication
import com.dexter.kotlinbaseapp.database.db.CineWorldDb
import com.dexter.kotlinbaseapp.utils.Constants
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val mApp: Application) {
     private var INSTANCE: CineWorldDb? = null
     private val sLock = Any()

    @Provides
    @PerApplication
    @AppContext
    fun provideAppContext(): Context {
        return mApp
    }

    @Provides
    @PerApplication
    fun provideResources(): Resources {
        return mApp.resources
    }

    @Provides
    @PerApplication
    fun providesDatabase(): CineWorldDb {
        synchronized(sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(provideAppContext(), CineWorldDb::class.java!!, Constants.DATABASE_NAME).build()
            }
            return INSTANCE as CineWorldDb
        }
    }
}

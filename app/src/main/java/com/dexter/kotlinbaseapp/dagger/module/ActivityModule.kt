package com.dexter.kotlinbaseapp.dagger.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.dexter.kotlinbaseapp.dagger.qualifier.ActivityContext
import com.dexter.kotlinbaseapp.dagger.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    @PerActivity
    @ActivityContext
    fun provideActivityContext(): Context {
        return mActivity
    }
}

package com.dexter.kotlinbaseapp.dagger.components

import android.content.Context
import com.dexter.kotlinbaseapp.dagger.module.ActivityModule
import com.dexter.kotlinbaseapp.dagger.qualifier.ActivityContext
import com.dexter.kotlinbaseapp.dagger.scopes.PerActivity

import dagger.Component

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {

    @ActivityContext
    fun activityContext(): Context

    /**
     * add inject method for all activities here
     */
}

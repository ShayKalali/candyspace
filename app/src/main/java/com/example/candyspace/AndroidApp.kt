package com.example.candyspace

import com.example.candyspace.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class AndroidApp : DaggerApplication() {

    companion object {
        lateinit var self: AndroidApp
    }

    override fun applicationInjector(): AndroidInjector<out AndroidApp> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this).inject(this)
        self = this
    }
}
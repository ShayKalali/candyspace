package com.example.candyspace.di.module

import android.app.Application
import android.content.Context
import com.example.candyspace.AndroidApp
import com.example.candyspace.di.qualifier.ApplicationContext
import com.example.candyspace.di.scope.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    @ApplicationContext
    @Binds
    @ApplicationScope
    abstract fun bindContext(application: AndroidApp): Context


    @Binds
    @ApplicationScope
    abstract fun bindApplication(application: AndroidApp): Application
}
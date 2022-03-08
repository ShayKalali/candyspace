package com.example.candyspace.di.module

import android.app.Application
import android.content.Context
import com.example.candyspace.AndroidApp
import com.example.candyspace.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule {
    @ApplicationContext
    @Binds
    @Singleton
    abstract fun bindContext(application: AndroidApp): Context


    @Binds
    @Singleton
    abstract fun bindApplication(application: AndroidApp): Application

}
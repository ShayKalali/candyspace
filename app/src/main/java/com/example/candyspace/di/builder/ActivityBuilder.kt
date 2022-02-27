package com.example.candyspace.di.builder

import com.example.candyspace.di.scope.ActivityScope
import com.example.candyspace.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}
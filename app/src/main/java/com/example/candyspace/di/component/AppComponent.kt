package com.example.candyspace.di.component


import com.example.candyspace.AndroidApp
import com.example.candyspace.di.builder.ActivityBuilder
import com.example.candyspace.di.module.ApplicationModule
import com.example.candyspace.di.module.ViewModelModule
import com.example.candyspace.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@ApplicationScope
@Component(modules = [ViewModelModule::class, ApplicationModule::class, ActivityBuilder::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<AndroidApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<AndroidApp>
}
package com.example.candyspace.di.module


import androidx.lifecycle.ViewModelProvider
import com.example.candyspace.di.scope.ApplicationScope
import com.example.candyspace.logic.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelModule {

    @Binds
    @ApplicationScope
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
package com.example.candyspace.ui.fragment.userlist

import com.example.candyspace.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserListFragmentProvider {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun provideUserListFragmentFactory(): UserListFragment
}
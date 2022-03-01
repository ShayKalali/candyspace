package com.example.candyspace.di.builder

import com.example.candyspace.di.scope.ActivityScope
import com.example.candyspace.ui.activity.MainActivity
import com.example.candyspace.ui.fragment.userdetail.UserDetailFragmentProvider
import com.example.candyspace.ui.fragment.userlist.UserListFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [UserListFragmentProvider::class, UserDetailFragmentProvider::class])
    abstract fun mainActivity(): MainActivity
}
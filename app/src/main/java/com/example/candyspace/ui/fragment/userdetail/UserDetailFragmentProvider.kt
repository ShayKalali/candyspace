package com.example.candyspace.ui.fragment.userdetail

import com.example.candyspace.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
 abstract class UserDetailFragmentProvider {

     @FragmentScope
     @ContributesAndroidInjector
     abstract fun provideUserDetailFragmentFactory() : UserDetailFragment
}
package com.freshdigitable.dlfeey.di

import androidx.recyclerview.widget.RecyclerView
import com.freshdigitable.dlfeey.feed.MainActivity
import com.freshdigitable.dlfeey.feed.MainActivityModule
import com.freshdigitable.dlfeey.feed.NavigationDispatcher
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            ActivityModule::class
        ]
    )
    fun contributeMainActivity(): MainActivity
}

@Module
object ActivityModule {
    @JvmStatic
    @Provides
    @ActivityScoped
    fun provideRecycledViewPool(): RecyclerView.RecycledViewPool {
        return RecyclerView.RecycledViewPool()
    }

    @JvmStatic
    @Provides
    @ActivityScoped
    fun provideNavigationDispatcher(): NavigationDispatcher {
        return NavigationDispatcher()
    }
}

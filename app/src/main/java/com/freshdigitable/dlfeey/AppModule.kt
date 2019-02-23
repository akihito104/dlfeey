package com.freshdigitable.dlfeey

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient()
        }
    }

    @Binds
    abstract fun bindViewModelProviderFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory
}

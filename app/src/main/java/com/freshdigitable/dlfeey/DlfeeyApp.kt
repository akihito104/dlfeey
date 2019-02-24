package com.freshdigitable.dlfeey

import android.app.Activity
import android.app.Application
import com.freshdigitable.dlfeey.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class DlfeeyApp : Application(), HasActivityInjector {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build().inject(this)
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}

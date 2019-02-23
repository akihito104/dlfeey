package com.freshdigitable.dlfeey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FeedFragment())
            .commit()
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return injector
    }
}

@Module
interface MainActivityModule {
    @ContributesAndroidInjector
    fun contributeFeedFragment(): FeedFragment

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel) : ViewModel
}

package com.freshdigitable.dlfeey.feed

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.freshdigitable.dlfeey.R
import com.freshdigitable.dlfeey.di.ActivityScoped
import com.freshdigitable.dlfeey.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var navigation: FeedNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.dispatcher.postEvent(FeedActivityEvent.Init)
    }

    override fun onBackPressed() {
        navigation.dispatcher.postEvent(FeedActivityEvent.Back)
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return injector
    }
}

enum class Category(
    @StringRes val title: Int,
    val url: String
) {
    ALL(R.string.category_all, "http://b.hatena.ne.jp/hotentry.rss"),
    SOCIAL(R.string.category_social, "http://b.hatena.ne.jp/hotentry/social.rss"),
    ECONOMICS(R.string.category_economics, "http://b.hatena.ne.jp/hotentry/economics.rss"),
    LIFE(R.string.category_life, "http://b.hatena.ne.jp/hotentry/life.rss"),
}

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun bindsAppCompatActivity(activity: MainActivity): AppCompatActivity

    @ContributesAndroidInjector
    abstract fun contributeFeedFragment(): FeedFragment

    @ContributesAndroidInjector
    abstract fun contributeFeedDetailFragment(): FeedDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedDetailViewModel::class)
    abstract fun bindFeedDetailViewModel(viewModel: FeedDetailViewModel): ViewModel

    @Module
    companion object {
        @JvmStatic
        @Provides
        @ActivityScoped
        fun provideFeedNavigation(dispatcher: NavigationDispatcher, activity: AppCompatActivity): FeedNavigation {
            return FeedNavigation(dispatcher, activity, R.id.main_container)
        }
    }
}

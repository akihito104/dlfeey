package com.freshdigitable.dlfeey

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
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

        val viewPager = findViewById<ViewPager>(R.id.main_pager)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return FeedFragment.newInstance(Category.values()[position].url)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return applicationContext.getString(Category.values()[position].title)
            }

            override fun getCount(): Int = Category.values().size
        }
        val tabs = findViewById<TabLayout>(R.id.main_tabs)
        tabs.setupWithViewPager(viewPager)
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
interface MainActivityModule {
    @ContributesAndroidInjector
    fun contributeFeedFragment(): FeedFragment

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel) : ViewModel
}

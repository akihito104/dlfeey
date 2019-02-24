package com.freshdigitable.dlfeey.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.freshdigitable.dlfeey.R
import com.google.android.material.tabs.TabLayout

class FeedPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager>(R.id.main_pager)
        viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return FeedFragment.newInstance(FeedCategory.values()[position].url)
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return view.context.getString(FeedCategory.values()[position].title)
            }

            override fun getCount(): Int = FeedCategory.values().size
        }
        val tabs = view.findViewById<TabLayout>(R.id.main_tabs)
        tabs.setupWithViewPager(viewPager)
    }
}

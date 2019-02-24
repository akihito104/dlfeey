package com.freshdigitable.dlfeey.feed

import androidx.annotation.StringRes
import com.freshdigitable.dlfeey.R

enum class FeedCategory(
    @StringRes val title: Int,
    val url: String
) {
    ALL(R.string.category_all, "http://b.hatena.ne.jp/hotentry.rss"),
    SOCIAL(R.string.category_social, "http://b.hatena.ne.jp/hotentry/social.rss"),
    ECONOMICS(R.string.category_economics, "http://b.hatena.ne.jp/hotentry/economics.rss"),
    LIFE(R.string.category_life, "http://b.hatena.ne.jp/hotentry/life.rss"),
}

package com.freshdigitable.dlfeey

import androidx.lifecycle.ViewModel

class FeedViewModel(
) : ViewModel() {
    private val repository = FeedRepository()

    val feed = repository.feed

    fun loadFeed(url: String) {
        repository.load(url)
    }

}

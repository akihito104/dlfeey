package com.freshdigitable.dlfeey

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    val feed = repository.feed

    fun loadFeed(url: String) {
        repository.load(url)
    }
}

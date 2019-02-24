package com.freshdigitable.dlfeey.feed

import androidx.lifecycle.ViewModel
import com.rometools.rome.feed.synd.SyndEntry
import javax.inject.Inject

class FeedDetailViewModel @Inject constructor(
    private val repository: FeedRepository
) : ViewModel() {

    fun getItem(uri: String): SyndEntry? {
        return repository.findEntry(uri)
    }
}

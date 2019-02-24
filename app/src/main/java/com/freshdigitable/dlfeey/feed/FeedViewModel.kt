package com.freshdigitable.dlfeey.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rometools.rome.feed.synd.SyndFeed
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    repository: FeedRepository
) : ViewModel() {

    private val url = MutableLiveData<String>()

    val feed: LiveData<SyndFeed?> = Transformations.switchMap(url, repository::getFeed)

    fun loadFeed(url: String) {
        this.url.postValue(url)
    }
}

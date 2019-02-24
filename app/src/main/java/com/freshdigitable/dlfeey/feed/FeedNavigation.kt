package com.freshdigitable.dlfeey.feed

import android.util.Log
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.BehaviorSubject

class FeedNavigation(
    val dispatcher: NavigationDispatcher,
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int
) {

    private val state : LiveData<FeedActivityState?>

    init {
        state = dispatcher.emitter.map { event ->
            Log.d("FeedNavigation", "event: $event")
            return@map when (event) {
                FeedActivityEvent.Init -> FeedActivityState.FeedLists
                is FeedActivityEvent.ShowItemDetail -> FeedActivityState.FeedItemDetail(event.uri)
            }
        }.toFlowable(BackpressureStrategy.BUFFER).toLiveData()

        state.observe(activity, Observer { s ->
            Log.d("FeedNavigation", "state: $s")
            when (s) {
                is FeedActivityState.FeedLists -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, FeedPagerFragment())
                        .commit()
                }
                is FeedActivityState.FeedItemDetail -> {
                    Toast.makeText(activity, s.uri, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

class NavigationDispatcher {
    val emitter = BehaviorSubject.create<FeedActivityEvent>()

    fun postEvent(event: FeedActivityEvent) {
        emitter.onNext(event)
    }
}

sealed class FeedActivityEvent {
    object Init : FeedActivityEvent()

    data class ShowItemDetail(val uri: String) : FeedActivityEvent()
}

sealed class FeedActivityState {
    object FeedLists : FeedActivityState()
    data class FeedItemDetail(val uri: String) : FeedActivityState()
}

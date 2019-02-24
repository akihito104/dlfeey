package com.freshdigitable.dlfeey.feed

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
    private val currentState: FeedActivityState?
        get() = state.value

    init {
        state = dispatcher.emitter.map { event ->
            return@map when (event) {
                FeedActivityEvent.Init -> FeedActivityState.FeedLists
                is FeedActivityEvent.ShowItemDetail -> FeedActivityState.FeedItemDetail(event.uri)
                FeedActivityEvent.Back -> {
                    if (currentState is FeedActivityState.FeedItemDetail) {
                        FeedActivityState.FeedLists
                    } else {
                        FeedActivityState.Halt
                    }
                }
            }
        }.toFlowable(BackpressureStrategy.BUFFER).toLiveData()

        state.observe(activity, Observer { s ->
            when (s) {
                is FeedActivityState.FeedLists -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, FeedPagerFragment())
                        .commit()
                }
                is FeedActivityState.FeedItemDetail -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, FeedDetailFragment.newInstance(s.uri))
                        .commit()
                }
                FeedActivityState.Halt -> activity.finish()
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

    object Back : FeedActivityEvent()
}

sealed class FeedActivityState {
    object FeedLists : FeedActivityState()

    data class FeedItemDetail(val uri: String) : FeedActivityState()

    object Halt : FeedActivityState()
}

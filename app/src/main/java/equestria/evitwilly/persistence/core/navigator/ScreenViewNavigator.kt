package equestria.evitwilly.persistence.core.navigator

import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import equestria.evitwilly.persistence.core.cache.KeyedMemoryTypedCache

class ScreenViewNavigator(
    private val frameLayoutView: FrameLayout,
    private val savedInstanceState: Bundle?,
    private val activityLifecycle: Lifecycle,
    private val backstackCache: KeyedMemoryTypedCache<List<ScreenView>>
) {

    private val backstack = mutableListOf<ScreenView>()

    val has_posibillity_to_navigate_back: Boolean
        get() = backstack.size > 1

    init {
        backstack.clear()

        val cachedBackstack = backstackCache.read(backstack_key).orEmpty()

        var index = 0
        while(index < cachedBackstack.size) {
            push(cachedBackstack[index])
            index++
        }
    }

    fun push(screenView: ScreenView) {
        screenView.changeNavigator(this)
        backstack.add(screenView)
        backstackCache.save(backstack_key, backstack)
        updateUIWhenViewHasBeenAddedInBackstack(screenView)
    }

    fun pop(): Boolean {
        val removedScreen = backstack.removeLastOrNull()
        if (removedScreen != null) {
            removedScreen.clearNavigator()
            backstackCache.save(backstack_key, backstack)
            updateUIWhenViewHasBeenRemovedFromBackstack(removedScreen)

            val currentScreen = backstack.lastOrNull()
            return if (currentScreen != null) {
                updateUIWhenViewHasBeenAddedInBackstack(currentScreen)
                true
            } else {
                false
            }
        }
        return false
    }

    private fun updateUIWhenViewHasBeenRemovedFromBackstack(screen: ScreenView) {
        activityLifecycle.removeObserver(screen)
        screen.cleanCachedData()
        frameLayoutView.removeView(screen.view)
    }

    private fun updateUIWhenViewHasBeenAddedInBackstack(screenView: ScreenView) {
        if (screenView.view != null) return

        activityLifecycle.addObserver(screenView)

        val newScreenView = screenView.createView(frameLayoutView.context, savedInstanceState)
        newScreenView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        frameLayoutView.addView(newScreenView)
    }

    companion object {
        private const val backstack_key = "backstack"
    }

}
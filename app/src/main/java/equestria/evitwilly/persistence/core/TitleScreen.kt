package equestria.evitwilly.persistence.core

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.extensions.*
import equestria.evitwilly.persistence.core.navigator.ScreenView
import equestria.evitwilly.persistence.core.theme.components.CoreFrameLayout
import equestria.evitwilly.persistence.core.theme.components.CoreTextView
import equestria.evitwilly.persistence.core.theme.components.CoreToolbarButton

abstract class TitleScreen: ScreenView() {

    abstract fun content(context: Context, bundle: Bundle?): View

    private var toolbarTitleView: TextView? = null
    private var menuButtonView: CoreToolbarButton? = null

    protected fun changeMenuButtonIsVisible(visible: Boolean) {
        menuButtonView?.isVisible = visible
    }

    protected fun changeMenuButtonClickListener(listener: () -> Unit) {
        menuButtonView?.setOnClickListener { listener.invoke() }
    }

    protected fun changeMenuButtonImageResource(@DrawableRes resource: Int) {
        menuButtonView?.changeImageResource(resource)
    }

    protected fun changeTitle(text: String) {
        toolbarTitleView?.text = text
    }

    override fun view(context: Context, bundle: Bundle?): View {
        val rootView = CoreFrameLayout(context)
        rootView.isClickable = true
        rootView.isFocusable = true

        val toolbarHeight = context.dp(toolbar_height)
        val toolbarView = FrameLayout(context)
        toolbarView.elevation = context.dp(8f)
        toolbarView.layoutParams(frameLayoutParams().matchWidth().height(toolbarHeight))
        rootView.addView(toolbarView)

        val isBackButtonVisible = screenNavigator?.has_posibillity_to_navigate_back ?: false

        val toolbarTitleView = CoreTextView(context)
        toolbarTitleView.typeface = context.roboto_medium
        toolbarTitleView.fontSize(18f)
        val margin = if (isBackButtonVisible) context.dp(40) else context.dp(16)
        toolbarTitleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER)
            .marginStart(margin).marginEnd(margin))
        toolbarView.addView(toolbarTitleView)

        this.toolbarTitleView = toolbarTitleView

        val backButtonView = CoreToolbarButton(context)
        backButtonView.layoutParams(frameLayoutParams().width(backButtonView.size).height(backButtonView.size)
            .gravity(Gravity.START or Gravity.CENTER_VERTICAL))
        backButtonView.isVisible = isBackButtonVisible
        backButtonView.changeImageResource(R.drawable.ic_back)
        backButtonView.setOnClickListener {
            screenNavigator?.pop()
        }
        toolbarView.addView(backButtonView)

        val menuButtonView = CoreToolbarButton(context)
        menuButtonView.layoutParams(frameLayoutParams().width(backButtonView.size).height(backButtonView.size)
            .gravity(Gravity.END or Gravity.CENTER_VERTICAL))
        menuButtonView.isVisible = false
        this.menuButtonView = menuButtonView
        toolbarView.addView(menuButtonView)

        val contentView = content(context, bundle)
        contentView.layoutParams(frameLayoutParams().match().marginTop(toolbarHeight))
        rootView.addView(contentView)

        return rootView
    }

    override fun onDestroy(owner: LifecycleOwner) {
        toolbarTitleView = null
        menuButtonView = null
        super.onDestroy(owner)
    }

    companion object {
        private const val toolbar_height = 48
    }

}
package equestria.evitwilly.persistence.core

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.colors.CoreColors
import equestria.evitwilly.persistence.core.extensions.*
import equestria.evitwilly.persistence.core.navigator.ScreenView

abstract class TitleScreen: ScreenView() {

    abstract fun content(context: Context, bundle: Bundle?): View

    private var toolbarTitleView: TextView? = null

    protected fun changeTitle(text: String) {
        toolbarTitleView?.text = text
    }

    override fun view(context: Context, bundle: Bundle?): View {
        val rootView = FrameLayout(context)
        rootView.isClickable = true
        rootView.isFocusable = true
        rootView.setBackgroundColor(CoreColors.white)

        val toolbarHeight = context.dp(toolbar_height)
        val toolbarView = FrameLayout(context)
        toolbarView.elevation = context.dp(8f)
        toolbarView.layoutParams(frameLayoutParams().matchWidth().height(toolbarHeight))
        rootView.addView(toolbarView)

        val isBackButtonVisible = screenNavigator?.has_posibillity_to_navigate_back ?: false

        val toolbarTitleView = TextView(context)
        toolbarTitleView.typeface = context.roboto_medium
        toolbarTitleView.fontSize(18f)
        toolbarTitleView.setTextColor(CoreColors.black)
        val margin = if (isBackButtonVisible) context.dp(40) else context.dp(16)
        toolbarTitleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER)
            .marginStart(margin).marginEnd(margin))
        toolbarView.addView(toolbarTitleView)

        this.toolbarTitleView = toolbarTitleView

        val backButtonView = FrameLayout(context)
        backButtonView.background = RippleDrawable(
            ColorStateList.valueOf(CoreColors.greenDark),
            GradientDrawable(), GradientDrawable().apply {
                cornerRadius = context.dp(50f)
                setColor(CoreColors.white)
            }
        )
        backButtonView.layoutParams(frameLayoutParams().width(context.dp(40)).height(context.dp(40))
            .gravity(Gravity.START or Gravity.CENTER_VERTICAL))
        backButtonView.isClickable = true
        backButtonView.isVisible = isBackButtonVisible
        backButtonView.setOnClickListener {
            screenNavigator?.pop()
        }
        toolbarView.addView(backButtonView)

        val backIconView = ImageView(context)
        backIconView.setImageResource(R.drawable.ic_back)
        backIconView.setColorFilter(CoreColors.black)
        backIconView.layoutParams(frameLayoutParams().width(context.dp(24)).height(context.dp(24))
            .gravity(Gravity.CENTER))
        backButtonView.addView(backIconView)

        val contentView = content(context, bundle)
        contentView.layoutParams(frameLayoutParams().match().marginTop(toolbarHeight))
        rootView.addView(contentView)

        return rootView
    }

    override fun onDestroy(owner: LifecycleOwner) {
        toolbarTitleView = null
        super.onDestroy(owner)
    }

    companion object {
        private const val toolbar_height = 48
    }

}
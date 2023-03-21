package equestria.evitwilly.persistence.core.theme.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.colors.CoreColors
import equestria.evitwilly.persistence.core.extensions.dp
import equestria.evitwilly.persistence.core.extensions.frameLayoutParams
import equestria.evitwilly.persistence.core.extensions.layoutParams
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CoreToolbarButton(context: Context): FrameLayout(context) {

    private val themeManager = (context.applicationContext as App).themeManager

    val size: Int
        get() {
            val theme = themeManager.selected_theme
            val sizeView = theme.toolbarButtonIconSize + theme.toolbarButtonIconPadding * 2
            return context.dp(sizeView)
        }

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        background = RippleDrawable(
            ColorStateList.valueOf(theme.toolbarButtonRippleColor),
            GradientDrawable(), GradientDrawable().apply {
                cornerRadius = context.dp(50f)
                setColor(CoreColors.white)
            }
        )

        backIconView.layoutParams(frameLayoutParams()
            .width(context.dp(theme.toolbarButtonIconSize))
            .height(context.dp(theme.toolbarButtonIconSize))
            .gravity(Gravity.CENTER))

        backIconView.setColorFilter(theme.toolbarButtonIconTint)
    }

    private val backIconView = ImageView(context)

    init {
        isClickable = true

        addView(backIconView)
    }

    fun changeImageResource(@DrawableRes resource: Int) {
        backIconView.setImageResource(resource)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

}
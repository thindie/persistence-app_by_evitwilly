package equestria.evitwilly.persistence.core.theme.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.extensions.dp
import equestria.evitwilly.persistence.core.extensions.roboto_medium
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CoreButton(context: Context): AppCompatTextView(context) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        background = RippleDrawable(
            ColorStateList.valueOf(theme.buttonRippleColor),
            GradientDrawable().apply {
                setColor(theme.buttonBackgroundColor)
                cornerRadius = context.dp(theme.buttonBorderRadius)
            }, null
        )
        setTextColor(theme.buttonTextColor)
    }

    private val themeManager = (context.applicationContext as App).themeManager

    init {
        isClickable = true
        gravity = Gravity.CENTER
        typeface = context.roboto_medium
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
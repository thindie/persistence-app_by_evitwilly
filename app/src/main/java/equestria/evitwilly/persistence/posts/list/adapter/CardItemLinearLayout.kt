package equestria.evitwilly.persistence.posts.list.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.widget.LinearLayout
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.extensions.dp
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CardItemLinearLayout(context: Context): LinearLayout(context) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        background = RippleDrawable(
            ColorStateList.valueOf(theme.cardItemRippleColor),
            GradientDrawable().apply {
                cornerRadius = context.dp(theme.cardItemCornerRadius)
                setColor(theme.cardItemBackgroundColor)
            }, null
        )
    }

    private val themeManager = (context.applicationContext as App).themeManager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

}
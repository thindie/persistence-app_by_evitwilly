package equestria.evitwilly.persistence.core.theme.components

import android.content.Context
import android.widget.LinearLayout
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CoreLinearLayout(context: Context): LinearLayout(context) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        setBackgroundColor(theme.backgroundColor)
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
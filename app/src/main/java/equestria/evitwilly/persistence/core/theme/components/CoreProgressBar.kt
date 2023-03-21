package equestria.evitwilly.persistence.core.theme.components

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ProgressBar
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CoreProgressBar(context: Context): ProgressBar(context) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        progressTintList = ColorStateList.valueOf(theme.progressTintColor)
    }

    private val themeManager = (context.applicationContext as App).themeManager

    init {
        isIndeterminate = false
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
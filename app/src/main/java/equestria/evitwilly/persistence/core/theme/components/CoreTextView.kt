package equestria.evitwilly.persistence.core.theme.components

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.theme.CoreTheme

class CoreTextView(context: Context): AppCompatTextView(context) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        setTextColor(theme.textColor)
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
package equestria.evitwilly.persistence.core.theme

import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.cache.PersistenceSimpleDataStorage

class CoreThemeManager(private val themeDataStorage: PersistenceSimpleDataStorage) {

    private val listeners = mutableListOf<(CoreTheme) -> Unit>()

    private var currentTheme = CoreTheme.values()[themeDataStorage.int(theme_key, CoreTheme.LIGHT.ordinal)]

    val selected_theme: CoreTheme
        get() = currentTheme

    val menu_button_icon_drawable_resource: Int
        get() = if (currentTheme == CoreTheme.LIGHT) R.drawable.ic_dark_theme else R.drawable.ic_light_theme

    fun listenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.add(listener)
        listener.invoke(currentTheme)
    }

    fun doNotListenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.remove(listener)
    }

    fun toggleTheme() {
        currentTheme = if (currentTheme == CoreTheme.LIGHT) CoreTheme.DARK else CoreTheme.LIGHT
        themeDataStorage.save(theme_key, currentTheme.ordinal)
        listeners.forEach { listener -> listener.invoke(currentTheme) }
    }

    companion object {
        private const val theme_key = "CoreThemeManager_key"
    }

}
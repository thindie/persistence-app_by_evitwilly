package equestria.evitwilly.persistence.core

import android.app.Application
import equestria.evitwilly.persistence.core.internet.BackgroundRunner
import equestria.evitwilly.persistence.core.internet.UiRunner
import equestria.evitwilly.persistence.core.cache.KeyedMemoryTypedCache
import equestria.evitwilly.persistence.core.internet.RestApiRequester
import equestria.evitwilly.persistence.core.navigator.ScreenView
import equestria.evitwilly.persistence.core.viewmodel.PersistenceViewModel

class App: Application() {

    val screenMemoryTypedCache = KeyedMemoryTypedCache<List<ScreenView>>()
    val viewModelMemoryTypedCache = KeyedMemoryTypedCache<PersistenceViewModel>()

    val uiRunner = UiRunner()
    val backgroundRunner = BackgroundRunner()
    val requester = RestApiRequester()

}
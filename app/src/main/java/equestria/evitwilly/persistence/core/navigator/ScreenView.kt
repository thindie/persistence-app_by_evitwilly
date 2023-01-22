package equestria.evitwilly.persistence.core.navigator

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.cache.KeyedMemoryTypedCache
import equestria.evitwilly.persistence.core.viewmodel.PersistenceViewModel

abstract class ScreenView: DefaultLifecycleObserver {

    protected var viewModel: PersistenceViewModel? = null
    protected open fun viewModelConstructor(ctx: Context): PersistenceViewModel? = null

    var view: View? = null

    private var viewModelCache: KeyedMemoryTypedCache<PersistenceViewModel>? = null

    protected var screenNavigator: ScreenViewNavigator? = null

    fun changeNavigator(navigator: ScreenViewNavigator) {
        screenNavigator = navigator
    }

    fun clearNavigator() {
        screenNavigator = null
    }

    fun createView(context: Context, bundle: Bundle?): View {
        viewModelCache = (context.applicationContext as? App)?.viewModelMemoryTypedCache
        viewModel = viewModelCache?.read(view_model_key) ?: viewModelConstructor(context)

        val myView = view(context, bundle)

        view = myView
        return myView
    }

    protected abstract fun view(context: Context, bundle: Bundle?): View

    fun cleanCachedData() {
        viewModel?.onDestroyComponentCalled()
        viewModelCache?.remove(view_model_key)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        val viewModel = viewModel
        if (viewModel != null) {
            viewModel.onDestroyViewCalled()
            viewModelCache?.save(view_model_key, viewModel)
        }
        viewModelCache = null
        view = null

        clearNavigator()

        super.onDestroy(owner)
    }

    companion object {
        private const val view_model_key = "viewModel"
    }

}
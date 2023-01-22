package equestria.evitwilly.persistence.core

import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import equestria.evitwilly.persistence.posts.list.PostListScreen
import equestria.evitwilly.persistence.core.navigator.ScreenViewNavigator

class MainActivity : AppCompatActivity() {

    private var screenViewNavigator: ScreenViewNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = FrameLayout(this)
        setContentView(container)

        val screenViewNavigator = ScreenViewNavigator(
            container,
            savedInstanceState,
            lifecycle,
            (application as App).screenMemoryTypedCache
        )
        this.screenViewNavigator = screenViewNavigator

        if (savedInstanceState == null) {
            screenViewNavigator.push(PostListScreen())
        }

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT) {
                if (!screenViewNavigator.pop()) {
                    finish()
                }
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!screenViewNavigator.pop()) {
                        finish()
                    }
                }
            })
        }

    }

    override fun onDestroy() {
        screenViewNavigator = null
        super.onDestroy()
    }

}
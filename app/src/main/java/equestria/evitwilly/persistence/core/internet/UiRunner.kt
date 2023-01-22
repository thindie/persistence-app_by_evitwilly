package equestria.evitwilly.persistence.core.internet

import android.os.Handler
import android.os.Looper

class UiRunner {

    private val handler = Handler(Looper.getMainLooper())

    fun runInUi(task: () -> Unit) {
        handler.post(task)
    }

}
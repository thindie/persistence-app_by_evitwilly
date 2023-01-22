package equestria.evitwilly.persistence.core.internet

import java.util.concurrent.Executors
import java.util.concurrent.Future

class BackgroundRunner {

    private val pool = Executors.newFixedThreadPool(2)

    fun runInBackground(task: () -> Unit): Future<*> {
        return pool.submit(task)
    }

}
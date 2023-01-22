package equestria.evitwilly.persistence.core.internet

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class RestApiRequester {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    fun get(url: String): Response {
        val request = Request.Builder()
            .url(url)
            .build()
        return okHttpClient.newCall(request).execute()
    }

}
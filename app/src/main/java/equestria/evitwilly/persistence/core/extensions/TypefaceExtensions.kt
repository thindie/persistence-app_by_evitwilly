package equestria.evitwilly.persistence.core.extensions

import android.content.Context
import android.graphics.Typeface

private val typefaceCache = hashMapOf<String, Typeface>()

private fun Context.typefaceGetter(key: String): Typeface {
    if (!typefaceCache.containsKey(key)) {
        val font = Typeface.createFromAsset(resources.assets, key)
        typefaceCache[key] = font
    }
    return typefaceCache[key]!!
}

val Context.roboto_bold: Typeface
    get() = typefaceGetter("Roboto-Bold.ttf")

val Context.roboto_medium: Typeface
    get() = typefaceGetter("Roboto-Medium.ttf")

val Context.roboto_regular: Typeface
    get() = typefaceGetter("Roboto-Regular.ttf")
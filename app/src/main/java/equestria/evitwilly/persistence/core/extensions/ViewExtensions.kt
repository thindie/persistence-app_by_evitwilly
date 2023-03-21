package equestria.evitwilly.persistence.core.extensions

import android.content.Context
import android.view.View
import equestria.evitwilly.persistence.core.layout.AbstractLP
import kotlin.math.roundToInt

fun Context.dp(dimen: Int) = (resources.displayMetrics.density * dimen).roundToInt()

fun Context.dp(dimen: Float) = (resources.displayMetrics.density * dimen)

fun View.padding(start: Int = paddingStart, top: Int = paddingTop, end: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(start, top, end, bottom)
}

fun View.padding(horizontal: Int, vertical: Int) {
    padding(horizontal, vertical, horizontal, vertical)
}

fun View.padding(all: Int) {
    padding(all, all, all, all)
}

fun View.layoutParams(params: AbstractLP<*, *>) {
    layoutParams = params.build()
}

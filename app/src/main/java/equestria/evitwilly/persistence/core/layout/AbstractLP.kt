package equestria.evitwilly.persistence.core.layout

import android.view.ViewGroup

abstract class AbstractLP<T : ViewGroup.LayoutParams, R>(private val params: T, private val match: Int, private val wrap: Int): LP<T> {

    abstract fun with(params: T) : R

    fun matchWidth() = with(params.apply { width = match })
    fun matchHeight() = with(params.apply { height = match })
    fun match() = with(params.apply {
        width = match
        height = match
    })

    fun wrapWidth() = with(params.apply { width = wrap })
    fun wrapHeight() = with(params.apply { height = wrap })
    fun wrap() = with(params.apply {
        width = wrap
        height = wrap
    })

    fun width(dp: Int) = with(params.apply { width = dp })
    fun height(dp: Int) = with(params.apply { height = dp })

    override fun build(): T = params
}
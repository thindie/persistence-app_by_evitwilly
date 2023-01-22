package equestria.evitwilly.persistence.core.extensions


import android.view.View
import android.view.ViewGroup
import equestria.evitwilly.persistence.core.layout.FrameLayoutLP
import equestria.evitwilly.persistence.core.layout.LinearLayoutLP
import equestria.evitwilly.persistence.core.layout.RecyclerViewLP
import equestria.evitwilly.persistence.core.layout.ViewGroupLP

fun linearLayoutParams() = LinearLayoutLP()
fun frameLayoutParams() = FrameLayoutLP()
fun viewGroupLayoutParams() = ViewGroupLP()
fun recyclerLayoutParams() = RecyclerViewLP()

fun ViewGroup.addView(vararg view: View) {
    view.forEach { view ->
        addView(view)
    }
}

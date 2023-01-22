package equestria.evitwilly.persistence.core.layout

import android.view.ViewGroup

interface LP<T : ViewGroup.LayoutParams> {
    fun build() : T
}
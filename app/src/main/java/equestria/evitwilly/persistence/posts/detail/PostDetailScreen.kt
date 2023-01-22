package equestria.evitwilly.persistence.posts.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.TitleScreen
import equestria.evitwilly.persistence.core.colors.CoreColors
import equestria.evitwilly.persistence.core.extensions.*
import equestria.evitwilly.persistence.posts.models.PostModel

class PostDetailScreen(private val model: PostModel): TitleScreen() {

    override fun content(context: Context, bundle: Bundle?): View {

        changeTitle(context.getString(R.string.about_post))

        val rootView = LinearLayout(context)
        rootView.orientation = LinearLayout.VERTICAL
        rootView.padding(context.dp(16))

        val titleView = TextView(context)
        titleView.typeface = context.roboto_bold
        titleView.setTextColor(CoreColors.black)
        titleView.fontSize(21f)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        rootView.addView(titleView)

        val contentView = TextView(context)
        contentView.fontSize(18f)
        contentView.typeface = context.roboto_regular
        contentView.setTextColor(CoreColors.black)
        contentView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(8)))
        rootView.addView(contentView)

        with(model) {
            title(titleView)
            content(contentView)
        }

        return rootView
    }

}
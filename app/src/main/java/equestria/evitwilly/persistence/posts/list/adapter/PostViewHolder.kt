package equestria.evitwilly.persistence.posts.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import equestria.evitwilly.persistence.core.colors.CoreColors
import equestria.evitwilly.persistence.core.extensions.*
import equestria.evitwilly.persistence.posts.models.PostModel
import equestria.evitwilly.persistence.core.extensions.fontSize
import equestria.evitwilly.persistence.core.theme.components.CoreTextView

class PostViewHolder(
    private val rootView: View,
    private val titleView: TextView,
    private val contentView: TextView
): RecyclerView.ViewHolder(rootView) {

    fun bind(model: PostModel, clickListener: (PostModel) -> Unit) {
        with(model) {
            title(titleView)
            content(contentView)
        }
        rootView.setOnClickListener { clickListener.invoke(model) }
    }

    companion object {

        fun create(parent: ViewGroup): PostViewHolder {
            val ctx = parent.context

            val rootView = CardItemLinearLayout(ctx)
            rootView.orientation = LinearLayout.VERTICAL
            rootView.isClickable = true
            rootView.elevation = ctx.dp(8f)
            rootView.layoutParams(recyclerLayoutParams().matchWidth().wrapHeight().margins(ctx.dp(8)))
            rootView.padding(ctx.dp(16))

            val titleView = CoreTextView(ctx)
            titleView.fontSize(18f)
            titleView.typeface = ctx.roboto_medium
            titleView.setTextColor(CoreColors.black)
            titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
            rootView.addView(titleView)

            val contentView = CoreTextView(ctx)
            contentView.fontSize(15f)
            contentView.typeface = ctx.roboto_regular
            contentView.setTextColor(CoreColors.black)
            contentView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(ctx.dp(8)))
            rootView.addView(contentView)

            return PostViewHolder(rootView, titleView, contentView)
        }

    }

}
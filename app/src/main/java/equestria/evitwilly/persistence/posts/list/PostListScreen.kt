package equestria.evitwilly.persistence.posts.list

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.TitleScreen
import equestria.evitwilly.persistence.core.colors.CoreColors
import equestria.evitwilly.persistence.core.extensions.dp
import equestria.evitwilly.persistence.core.extensions.frameLayoutParams
import equestria.evitwilly.persistence.core.extensions.layoutParams
import equestria.evitwilly.persistence.core.extensions.padding
import equestria.evitwilly.persistence.posts.data.PostRepository
import equestria.evitwilly.persistence.posts.detail.PostDetailScreen
import equestria.evitwilly.persistence.posts.list.adapter.PostListAdapter
import equestria.evitwilly.persistence.posts.list.viewmodel_states.PostListState
import equestria.evitwilly.persistence.core.extensions.fontSize

class PostListScreen: TitleScreen() {

    override fun viewModelConstructor(ctx: Context): PostListViewModel {
        val app = ctx.applicationContext as App
        return PostListViewModel(PostRepository(app.requester, app.uiRunner, app.backgroundRunner))
    }

    override fun content(context: Context, bundle: Bundle?): View {
        changeTitle(context.getString(R.string.albums))

        val rootView = FrameLayout(context)

        val adapter = PostListAdapter {
            screenNavigator?.push(PostDetailScreen(it))
        }
        val recyclerView = RecyclerView(context)
        recyclerView.adapter = adapter
        recyclerView.padding(context.dp(8))
        recyclerView.clipToPadding = false
        recyclerView.layoutManager = if (context.resources.configuration.orientation == ORIENTATION_PORTRAIT) {
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else {
            GridLayoutManager(context, 2)
        }
        recyclerView.layoutParams(frameLayoutParams().match())
        rootView.addView(recyclerView)

        val progress = ProgressBar(context)
        progress.isVisible = false
        progress.progressTintList = ColorStateList.valueOf(CoreColors.greenMedium)
        progress.isIndeterminate = true
        progress.layoutParams(frameLayoutParams().width(context.dp(48)).height(context.dp(48)).gravity(Gravity.CENTER))
        rootView.addView(progress)

        val errorLinearView = LinearLayout(context)
        errorLinearView.isVisible = false
        errorLinearView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER).margins(context.dp(16)))
        errorLinearView.orientation = LinearLayout.VERTICAL

        val titleErrorView = TextView(context)
        titleErrorView.setTextColor(CoreColors.black)
        titleErrorView.fontSize(16f)
        titleErrorView.setText(R.string.happen_error_try_again)
        errorLinearView.addView(titleErrorView)

        val viewModel = viewModel as PostListViewModel

        val tryAgainButtonView = TextView(context)
        tryAgainButtonView.fontSize(16f)
        tryAgainButtonView.background = RippleDrawable(
            ColorStateList.valueOf(CoreColors.greenDark),
            GradientDrawable().apply {
                setColor(CoreColors.greenMedium)
                cornerRadius = context.dp(12f)
            }, null
        )
        tryAgainButtonView.padding(horizontal = context.dp(16), vertical = context.dp(8))
        tryAgainButtonView.isClickable = true
        tryAgainButtonView.setOnClickListener { viewModel.posts() }

        viewModel.state.addChangedListener { state ->
            progress.isVisible = false
            errorLinearView.isVisible = false
            recyclerView.isVisible = false

            when (state) {
                is PostListState.Loading -> progress.isVisible = true
                is PostListState.Error -> errorLinearView.isVisible = true
                is PostListState.Success -> {
                    recyclerView.isVisible = true
                    adapter.submitList(state.items)
                }
                else -> {}
            }
        }

        return rootView
    }

}
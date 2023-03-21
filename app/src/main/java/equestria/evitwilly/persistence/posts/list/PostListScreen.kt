package equestria.evitwilly.persistence.posts.list

import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import equestria.evitwilly.persistence.R
import equestria.evitwilly.persistence.core.App
import equestria.evitwilly.persistence.core.TitleScreen
import equestria.evitwilly.persistence.core.extensions.*
import equestria.evitwilly.persistence.core.theme.components.*
import equestria.evitwilly.persistence.posts.data.PostRepository
import equestria.evitwilly.persistence.posts.detail.PostDetailScreen
import equestria.evitwilly.persistence.posts.list.adapter.PostListAdapter
import equestria.evitwilly.persistence.posts.list.viewmodel_states.PostListState

class PostListScreen: TitleScreen() {

    override fun viewModelConstructor(ctx: Context): PostListViewModel {
        val app = ctx.applicationContext as App
        return PostListViewModel(PostRepository(app.requester, app.uiRunner, app.backgroundRunner))
    }

    override fun content(context: Context, bundle: Bundle?): View {

        changeTitle(context.getString(R.string.posts))

        val themeManager = (context.applicationContext as App).themeManager

        changeMenuButtonIsVisible(true)
        changeMenuButtonImageResource(themeManager.menu_button_icon_drawable_resource)
        changeMenuButtonClickListener {
            themeManager.toggleTheme()
            changeMenuButtonImageResource(themeManager.menu_button_icon_drawable_resource)
        }

        val rootView = CoreFrameLayout(context)

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

        val progressView = CoreProgressBar(context)
        progressView.isVisible = false
        progressView.layoutParams(frameLayoutParams().width(context.dp(48)).height(context.dp(48)).gravity(Gravity.CENTER))
        rootView.addView(progressView)

        val errorLinearView = CoreLinearLayout(context)
        errorLinearView.orientation = LinearLayout.VERTICAL
        errorLinearView.isVisible = false
        errorLinearView.gravity = Gravity.CENTER
        errorLinearView.padding(context.dp(16))
        errorLinearView.layoutParams(frameLayoutParams().match())
        rootView.addView(errorLinearView)

        val titleErrorView = CoreTextView(context)
        titleErrorView.fontSize(16f)
        titleErrorView.setText(R.string.happen_error_try_again)
        titleErrorView.layoutParams(linearLayoutParams().wrap())
        errorLinearView.addView(titleErrorView)

        val viewModel = viewModel as PostListViewModel

        val tryAgainButtonView = CoreButton(context)
        tryAgainButtonView.setText(R.string.retry)
        tryAgainButtonView.fontSize(16f)
        tryAgainButtonView.padding(horizontal = context.dp(16), vertical = context.dp(8))
        tryAgainButtonView.setOnClickListener { viewModel.posts() }
        tryAgainButtonView.layoutParams(linearLayoutParams().width(context.dp(180))
            .wrapHeight().marginTop(context.dp(8)))
        errorLinearView.addView(tryAgainButtonView)

        viewModel.state.addChangedListener { state ->
            progressView.isVisible = false
            errorLinearView.isVisible = false
            recyclerView.isVisible = false

            when (state) {
                is PostListState.Loading -> progressView.isVisible = true
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
package equestria.evitwilly.persistence.posts.list

import equestria.evitwilly.persistence.core.viewmodel.PersistenceViewModel
import equestria.evitwilly.persistence.core.observable_property.ObservableProperty
import equestria.evitwilly.persistence.core.observable_property.MutableObservableProperty
import equestria.evitwilly.persistence.posts.data.PostRepository
import equestria.evitwilly.persistence.posts.list.viewmodel_states.PostListState
import java.util.concurrent.Future

class PostListViewModel(private val repo: PostRepository): PersistenceViewModel {

    private val futures = mutableListOf<Future<*>>()

    private val _state: MutableObservableProperty<PostListState> = MutableObservableProperty(PostListState.Loading)
    val state: ObservableProperty<PostListState>
        get() = _state

    init {
        posts()
    }

    fun posts() {
        _state.value = PostListState.Loading
        futures.add(repo.posts {
            _state.value = it
        })
    }

    override fun onDestroyViewCalled() {
        super.onDestroyViewCalled()
        _state.clearChangedListeners()
    }

    override fun onDestroyComponentCalled() {
        super.onDestroyComponentCalled()
        futures.forEach {
            it.cancel(true)
        }
    }

}
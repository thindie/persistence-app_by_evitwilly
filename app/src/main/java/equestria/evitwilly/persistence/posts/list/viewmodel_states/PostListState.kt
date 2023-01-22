package equestria.evitwilly.persistence.posts.list.viewmodel_states

import equestria.evitwilly.persistence.posts.models.PostModel

sealed interface PostListState {
    object Loading: PostListState
    object Error: PostListState
    class Success(val items: List<PostModel>): PostListState
}
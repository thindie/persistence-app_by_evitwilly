package equestria.evitwilly.persistence.posts.data

import equestria.evitwilly.persistence.core.internet.BackgroundRunner
import equestria.evitwilly.persistence.core.internet.RestApiRequester
import equestria.evitwilly.persistence.core.internet.UiRunner
import equestria.evitwilly.persistence.posts.list.viewmodel_states.PostListState
import equestria.evitwilly.persistence.posts.models.PostModel
import org.json.JSONArray

class PostRepository(
    private val requester: RestApiRequester,
    private val uiRunner: UiRunner,
    private val backgroundRunner: BackgroundRunner
) {

    fun posts(dataListener: (PostListState) -> Unit) = backgroundRunner.runInBackground {
        try {
            val response = requester.get(posts_path)
            if (response.isSuccessful) {
                val jsonArray = JSONArray(response.body?.string().orEmpty())
                val jsonArraySize = jsonArray.length()
                var currentAlbumIndex = 0
                val albumModels = mutableListOf<PostModel>()
                while (currentAlbumIndex < jsonArraySize) {
                    val currentAlbumJsonObject = jsonArray.getJSONObject(currentAlbumIndex)
                    albumModels.add(PostModel.createFromJson(currentAlbumJsonObject))
                    currentAlbumIndex++
                }
                uiRunner.runInUi {
                    dataListener.invoke(PostListState.Success(albumModels))
                }
            } else {
                uiRunner.runInUi {
                    dataListener.invoke(PostListState.Error)
                }
            }
        } catch (_: Exception) {
            uiRunner.runInUi {
                dataListener.invoke(PostListState.Error)
            }
        }
    }

    companion object {
        private const val posts_path = "https://jsonplaceholder.typicode.com/posts"
    }

}
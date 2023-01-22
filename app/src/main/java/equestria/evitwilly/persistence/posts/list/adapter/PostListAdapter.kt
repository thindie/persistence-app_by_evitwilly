package equestria.evitwilly.persistence.posts.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import equestria.evitwilly.persistence.posts.models.PostModel

class PostListAdapter(private val clickListener: (PostModel) -> Unit): ListAdapter<PostModel, PostViewHolder>(object: DiffUtil.ItemCallback<PostModel>() {
    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel) = oldItem == newItem
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel) = oldItem.id == newItem.id
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder.create(parent)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) { holder.bind(getItem(position), clickListener) }
}
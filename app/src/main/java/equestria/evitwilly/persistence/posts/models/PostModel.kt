package equestria.evitwilly.persistence.posts.models

import android.widget.TextView
import org.json.JSONObject

class PostModel(
    val id: Int,
    private val title: String,
    private val body: String
) {

    fun title(view: TextView) {
        view.text = title
    }

    fun content(view: TextView) {
        view.text = body
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is PostModel) return false

        return id == other.id && title == other.title && body == other.body
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + body.hashCode()
        return result
    }

    override fun toString(): String = "[id=$id, title=$title, body=$body]"

    companion object {

        fun createFromJson(jsonObject: JSONObject): PostModel {
            return PostModel(
                jsonObject.getInt("id"),
                jsonObject.getString("title"),
                jsonObject.optString("body")
            )
        }

    }

}
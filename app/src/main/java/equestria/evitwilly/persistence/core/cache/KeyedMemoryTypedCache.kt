package equestria.evitwilly.persistence.core.cache

class KeyedMemoryTypedCache<T> {

    private val internal = HashMap<String, T>()

    fun save(key: String, obj: T) {
        internal[key] = obj
    }

    fun read(key: String): T? {
        return internal[key]
    }

    fun remove(key: String): Boolean {
        return internal.remove(key) != null
    }

}
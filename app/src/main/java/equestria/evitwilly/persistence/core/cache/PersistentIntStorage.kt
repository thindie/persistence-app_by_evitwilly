package equestria.evitwilly.persistence.core.cache

interface PersistentIntStorage {
    fun int(key: String, default: Int): Int
    fun save(key: String, value: Int)
}
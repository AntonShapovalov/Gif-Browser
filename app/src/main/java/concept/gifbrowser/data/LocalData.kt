package concept.gifbrowser.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * Stores images data to [SharedPreferences]
 */
@Singleton
class LocalData @Inject constructor() {

    companion object {
        const val IMAGES_KEY = "IMAGES_KEY"
        const val UPDATED_KEY = "UPDATED_KEY"
    }

    @Inject lateinit var preferences: SharedPreferences
    @Inject lateinit var gson: Gson

    private val tokenType = object : TypeToken<List<ImageItem>>() {}.type
    private val expirationTime = TimeUnit.MINUTES.toMillis(5)

    fun saveRemoteData(list: List<GifEntry>) {
        val items = list.map {
            Timber.d("id=%s", it.id)
            ImageItem(it.id, it.images.preview_gif.url, it.images.original.url)
        }
        val values = getAllImages().toMutableList()
        values.addAll(items)
        val value = gson.toJson(values, tokenType)
        preferences.edit().apply {
            putString(IMAGES_KEY, value)
            putLong(UPDATED_KEY, System.currentTimeMillis())
        }.apply()
    }

    fun getImages(limit: Int = ITEMS_LIMIT, offset: Int = 0): List<ImageItem> {
        val start = limit * offset
        val end = start + limit
        val items = getAllImages()
        val size = items.size
        return when {
            size == 0 -> items
            start >= size -> emptyList()
            end >= size -> items.subList(start, size)
            else -> items.subList(start, end)
        }
    }

    private fun getAllImages(): List<ImageItem> {
        val value = preferences.getString(IMAGES_KEY, null)
        return value?.let { gson.fromJson<List<ImageItem>>(it, tokenType) } ?: emptyList()
    }

    fun isCacheExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val updatedTime = preferences.getLong(UPDATED_KEY, 0L)
        return abs(currentTime - updatedTime) > expirationTime
    }

    internal fun clear() {
        preferences.edit().apply {
            remove(IMAGES_KEY)
            remove(UPDATED_KEY)
        }.apply()
    }

}
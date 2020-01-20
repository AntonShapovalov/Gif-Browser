package concept.gifbrowser.data

import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides Gif Images data from [ApiService]
 */
@Singleton
class RemoteData @Inject constructor() {

    @Inject lateinit var apiService: ApiService

    private val _apiKey = "3wwoMIGohZT9K1gyqzPL7aSLIT7GB5xi"

    fun getImages(
        apiKey: String = _apiKey,
        limit: Int = ITEMS_LIMIT,
        offset: Int = 0
    ): Observable<List<GifEntry>> = apiService
        .getImages(apiKey, limit, offset)
        .map { if (it.isSuccessful) it.body()?.data else throw RuntimeException("Unable to get data!") }

}
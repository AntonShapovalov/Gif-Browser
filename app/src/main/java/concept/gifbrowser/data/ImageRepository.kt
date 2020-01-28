package concept.gifbrowser.data

import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

const val ITEMS_LIMIT = 25

/**
 * Provides images data from local cache or remote source
 */
@Singleton
class ImageRepository @Inject constructor() {

    @Inject lateinit var localData: LocalData
    @Inject lateinit var remoteData: RemoteData

    /**
     * Get images from local cache or from remote API starting from position [offset]
     * If local cache is expired - load data from API starting from position 0
     */
    fun getImages(offset: Int, limit: Int = ITEMS_LIMIT): Observable<List<ImageItem>> =
        localImages(offset, limit).switchIfEmpty(Observable.defer { remoteImages(offset, limit) })

    private fun localImages(position: Int, count: Int): Observable<List<ImageItem>> = Observable
        .fromCallable { localData.getImages(offset = position, limit = count) }
        .filter { it.isNotEmpty() && (position > 0 || !localData.isCacheExpired()) }

    private fun remoteImages(position: Int, count: Int): Observable<List<ImageItem>> = remoteData
        .getImages(offset = position, limit = count)
        .doOnNext { localData.saveRemoteData(it, isCacheExpired = position == 0) }
        .map { localData.getImages(offset = position, limit = count) }

}
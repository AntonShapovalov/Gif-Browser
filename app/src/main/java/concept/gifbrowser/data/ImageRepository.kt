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

    fun getImages(page: Int): Observable<List<ImageItem>> = Observable
        .fromCallable { localData.getImages(offset = page) }
        .filter { it.isNotEmpty() && !localData.isCacheExpired() }
        .concatWith(remoteData.getImages(offset = page)
            .doOnNext { localData.saveRemoteData(it) }
            .map { localData.getImages(offset = page) }
        )
        .take(1)

}
package concept.gifbrowser.ui.paging

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import concept.gifbrowser.data.ITEMS_LIMIT
import concept.gifbrowser.data.ImageItem
import concept.gifbrowser.data.ImageRepository
import concept.gifbrowser.ui.grid.GridViewModel
import concept.githubfavoriterepo.ui.activity.ImagesLoaded
import concept.githubfavoriterepo.ui.activity.StateError
import concept.githubfavoriterepo.ui.activity.StateLiveData
import concept.githubfavoriterepo.ui.activity.StateProgress
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Provides paged data for [GridViewModel]
 */
class PageDataSource(
    private val repository: ImageRepository,
    private val disposable: CompositeDisposable,
    private val state: StateLiveData
) : PageKeyedDataSource<Int, ImageItem>() {

    companion object {
        val pageListConfig: PagedList.Config
            get() = PagedList.Config.Builder()
                .setPageSize(ITEMS_LIMIT)
                .setEnablePlaceholders(false) // do not show empty views until data is available
                .build()
        val networkExecutor: ExecutorService = Executors.newFixedThreadPool(4)
    }

    private var retry: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageItem>
    ) {
        val page = 0
        // initially load 2 pages from API and save to local cache, but return only first page to UI
        val d = repository.getImages(offset = 0, limit = ITEMS_LIMIT * 2)
            .switchMap { repository.getImages(offset = 0) }
            .doOnSubscribe { state.postValue(StateProgress) }
            .subscribe({
                state.postValue(ImagesLoaded)
                retry = null
                callback.onResult(it, null, page + 1)
            }, {
                state.postValue(StateError(it))
                retry = { loadInitial(params, callback) }
            })
        disposable.add(d)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        val page = params.key // next page, start from 1
        val d = repository.getImages(offset = ITEMS_LIMIT * page)
            .doOnSubscribe { state.postValue(StateProgress) }
            .subscribe({
                state.postValue(ImagesLoaded)
                retry = null
                callback.onResult(it, page + 1)
            }, {
                state.postValue(StateError(it))
                retry = { loadAfter(params, callback) }
            })
        disposable.add(d)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ImageItem>
    ) = Unit

    /**
     *  ViewModel call this if previous state was [StateError]
     */
    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { networkExecutor.execute { it.invoke() } }
    }

}
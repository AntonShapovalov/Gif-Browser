package concept.gifbrowser.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import concept.gifbrowser.data.ImageItem
import concept.gifbrowser.data.ImageRepository
import concept.gifbrowser.ui.paging.PageDataSource
import concept.gifbrowser.ui.paging.PageDataSourceFactory
import concept.githubfavoriterepo.ui.activity.StateLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GridViewModel : ViewModel() {

    @Inject lateinit var repository: ImageRepository

    val state = StateLiveData()
    private var images: LiveData<PagedList<ImageItem>>? = null
    private var sourceFactory: PageDataSourceFactory? = null
    private val disposables = CompositeDisposable()

    fun getImages(): LiveData<PagedList<ImageItem>>? {
        if (images != null) return images
        val factory = PageDataSourceFactory(repository, disposables, state)
        sourceFactory = factory
        val config = PageDataSource.pageListConfig
        images = LivePagedListBuilder<Int, ImageItem>(factory, config)
            .setFetchExecutor(PageDataSource.networkExecutor)
            .build()
        return images
    }

    fun retry() {
        sourceFactory?.pageDataSource?.retry()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}
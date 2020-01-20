package concept.gifbrowser.ui.paging

import androidx.paging.DataSource
import concept.gifbrowser.data.ImageItem
import concept.gifbrowser.data.ImageRepository
import concept.gifbrowser.ui.grid.GridViewModel
import concept.githubfavoriterepo.ui.activity.StateLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 * Provides [PageDataSource] for [GridViewModel]
 */
class PageDataSourceFactory(
    private val repository: ImageRepository,
    private val disposable: CompositeDisposable,
    private val state: StateLiveData
) : DataSource.Factory<Int, ImageItem>() {

    var pageDataSource: PageDataSource? = null

    override fun create(): DataSource<Int, ImageItem> {
        val source = PageDataSource(repository, disposable, state)
        pageDataSource = source
        return source
    }

}
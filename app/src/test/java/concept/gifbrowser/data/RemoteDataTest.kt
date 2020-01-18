package concept.gifbrowser.data

import concept.gifbrowser.di.ApiModule
import concept.gifbrowser.di.DaggerApiTestComponent
import concept.gifbrowser.di.UtilsModule
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Test for [RemoteData]
 */
class RemoteDataTest {

    @Inject lateinit var remoteModel: RemoteData

    @Before
    fun setUp() = DaggerApiTestComponent.builder()
        .utilsModule(UtilsModule())
        .apiModule(ApiModule())
        .build()
        .inject(this)

    @Test
    fun getImages() {
        val testObserver = TestObserver<List<GifEntry>>()
        remoteModel.getImages(limit = 25, offset = 0)
            .doOnNext { list ->
                Assert.assertTrue(list.isNotEmpty())
                list.forEach {
                    println(it)
                    Assert.assertNotNull(it.images.preview_gif.url)
                    Assert.assertNotNull(it.images.original.url)
                }
            }
            .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

}
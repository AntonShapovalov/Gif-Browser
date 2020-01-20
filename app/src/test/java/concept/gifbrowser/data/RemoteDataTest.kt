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

    private val set = HashSet<String>()

    @Before
    fun setUp() = DaggerApiTestComponent.builder()
        .utilsModule(UtilsModule())
        .apiModule(ApiModule())
        .build()
        .inject(this)

    @Test
    fun getImagesOffset0() {
        getImages(0)
    }

    @Test
    fun getImagesOffset25() {
        getImages(25)
    }

    private fun getImages(_offset:Int) {
        val testObserver = TestObserver<List<GifEntry>>()
        remoteModel.getImages(limit = 25, offset = _offset)
            .doOnNext { list ->
                Assert.assertTrue(list.isNotEmpty())
                list.forEach {
                    println(it.id)
                    Assert.assertFalse(set.contains(it.id))
                    set.add(it.id)
                    Assert.assertNotNull(it.images.preview_gif)
                    Assert.assertNotNull(it.images.original)
                }
            }
            .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

}
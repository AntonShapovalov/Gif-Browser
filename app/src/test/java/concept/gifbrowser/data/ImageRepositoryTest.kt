package concept.gifbrowser.data

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ImageRepositoryTest {

    private lateinit var localData: LocalData
    private lateinit var remoteData: RemoteData
    private val repository: ImageRepository = ImageRepository()

    @Before
    fun setUp() {
        localData = mock(LocalData::class.java)
        remoteData = mock(RemoteData::class.java)
        repository.localData = localData
        repository.remoteData = remoteData
    }

    @Test
    fun getRemoteIfLocalEmpty() {
        `when`(localData.getImages()).thenReturn(emptyList(), localList())
        `when`(remoteData.getImages()).thenReturn(Observable.just(emptyList()))
        getRepositoryImages()
        verify(localData, times(2)).getImages()
        verify(localData, never()).isCacheExpired()
        verify(remoteData).getImages()
    }

    @Test
    fun getRemoteIfCacheExpired() {
        `when`(localData.getImages()).thenReturn(localList())
        `when`(localData.isCacheExpired()).thenReturn(true)
        `when`(remoteData.getImages()).thenReturn(Observable.just(emptyList()))
        getRepositoryImages()
        verify(localData, times(2)).getImages()
        verify(remoteData).getImages()
    }

    @Test
    fun getLocalImages() {
        `when`(localData.getImages()).thenReturn(localList())
        getRepositoryImages()
        verify(localData).getImages()
        verify(localData).isCacheExpired()
        verify(remoteData, never()).getImages()
    }

    private fun getRepositoryImages() {
        val testObserver = TestObserver<List<ImageItem>>()
        repository.getImages(0)
            .doOnNext {
                val savedArray = it.toTypedArray()
                val testArray = localList().toTypedArray()
                Assert.assertTrue(savedArray.contentEquals(testArray))
            }
            .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    private fun localList(): List<ImageItem> = listOf(
        ImageItem("1","p1", "o1")
    )

}
package concept.gifbrowser.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import concept.gifbrowser.di.AppModule
import concept.gifbrowser.di.DaggerAppTestComponent
import concept.gifbrowser.di.UtilsModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Android context test for [LocalData]
 */
@RunWith(AndroidJUnit4::class)
class LocalDataAndroidTest {

    @Inject lateinit var localData: LocalData

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        DaggerAppTestComponent.builder()
            .utilsModule(UtilsModule())
            .appModule(AppModule(context))
            .build()
            .inject(this)
        localData.clear()
    }

    @Test
    fun isCacheExpired() {
        Assert.assertTrue(localData.isCacheExpired())
        localData.saveRemoteData(getRemoteImages())
        Assert.assertFalse(localData.isCacheExpired())
    }

    @Test
    fun saveRemoteData() {
        localData.saveRemoteData(getRemoteImages())
        val savedArray = localData.getImages().toTypedArray()
        val testArray = getLocalImages().toTypedArray()
        Assert.assertTrue(savedArray.contentEquals(testArray))
    }

    @Test
    fun getImages() {
        // initial array is empty
        Assert.assertTrue(localData.getImages().isEmpty())
        // save first 25 items
        localData.saveRemoteData(getRemoteImages(0, 24))
        var savedItems = localData.getImages(limit = 25, offset = 0)
        Assert.assertEquals(25, savedItems.size)
        // try to get items more then exist in cache
        savedItems = localData.getImages(limit = 25, offset = 1)
        Assert.assertTrue(savedItems.isEmpty())
        // save next 10 items
        localData.saveRemoteData(getRemoteImages(25, 34))
        savedItems = localData.getImages(limit = 25, offset = 1)
        Assert.assertEquals(10, savedItems.size)
    }

    private fun getRemoteImages(start: Int = 0, end: Int = 24): List<GifEntry> {
        val items = ArrayList<GifEntry>()
        (start..end).forEach { i ->
            val pImage = GifImage("p$i")
            val oImage = GifImage("o$i")
            val images = GifImages(pImage, oImage)
            items.add(GifEntry(i.toString(), images))
        }
        return items
    }

    private fun getLocalImages(start: Int = 0, end: Int = 24): List<ImageItem> {
        val items = ArrayList<ImageItem>()
        (start..end).forEach { i ->
            val item = ImageItem("p$i", "o$i")
            items.add(item)
        }
        return items
    }

    @After
    fun tearDown() = localData.clear()

}